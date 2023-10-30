package oit.is.z2173.kaizi.janken.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2173.kaizi.janken.model.Match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oit.is.z2173.kaizi.janken.model.MatchMapper;
import oit.is.z2173.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2173.kaizi.janken.model.MatchInfo;

import java.util.concurrent.TimeUnit;

@Service
public class AsyncKekka {

    private boolean dbUpdated = false;

    @Autowired
    MatchMapper matchMapper;

    @Autowired
    MatchInfoMapper matchInfoMapper;
    
    private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

    public Match syncShowMatch(){
        Match match = matchMapper.selectActiveMatch();
        return match;
    }

    public void setDbUpdated(boolean dbUpdated){
        this.dbUpdated = dbUpdated;
    }

    @Async
    public void printKekka(SseEmitter emitter) {
        logger.info("printKekka START");
        try {
            while(true){
                if(!dbUpdated){
                    TimeUnit.MILLISECONDS.sleep(1000);
                    continue;
                }
                Match match = this.syncShowMatch();
                emitter.send(match);

                MatchInfo matchInfo = matchInfoMapper.selectByUsers(match.getUser1(), match.getUser2());
                matchInfo.setIsActive(false);
                matchInfoMapper.updateMatchInfo(matchInfo);

                TimeUnit.MILLISECONDS.sleep(1000);
                dbUpdated = false;
            }
        } catch (Exception e) {
            logger.error("Exception: " + e.getClass().getName() + ":" + e.getMessage());
        }finally{
            emitter.complete();
        }
    }
}

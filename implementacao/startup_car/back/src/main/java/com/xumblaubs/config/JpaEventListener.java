package com.xumblaubs.config;

import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaEventListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(JpaEventListener.class);

    @Override
    public void onPostInsert(PostInsertEvent event) {
        logger.info("📝 INSERT realizado na tabela: {} - ID: {}", 
            event.getEntity().getClass().getSimpleName(), 
            event.getId());
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        logger.info("🔄 UPDATE realizado na tabela: {} - ID: {}", 
            event.getEntity().getClass().getSimpleName(), 
            event.getId());
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        logger.info("🗑️ DELETE realizado na tabela: {} - ID: {}", 
            event.getEntity().getClass().getSimpleName(), 
            event.getId());
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}

package ru.ifmo.quant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.entities.AccountEntity;

import java.util.HashMap;

/**
 * Created by andrey on 08.11.2016.
 */
@Component
@Scope("prototype")
public class HandlingProcess implements InitializingBean {

    public static final String TASKS_LIST = "tasks-list";
    public static final String TASK = "task";
    public static final String NOTIFICATION = "notification";

    @Autowired
    private HandlingState handlingState;
    private AccountEntity accountEntity;
    private HashMap<String,Object> processParameters;
    private Authentication authentication;

    public void afterPropertiesSet() throws Exception {
        processParameters = new HashMap<String, Object>();
    }

    public HandlingState getHandlingState() {
        return handlingState;
    }

    public void setHandlingState(HandlingState handlingState) {
        this.handlingState = handlingState;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setParameter(String name, Object object) {
        processParameters.put(name, object);
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public <K> K getParameter(String name, Class<K> className) {
        return (K) processParameters.get(name);
    }

    public Object getParameter(String name) {
        return processParameters.get(name);
    }

    public void clearParameters() {
        processParameters.clear();
    }

    public void changeState(String stateName) {
        try {
            handlingState.changeExtractor(stateName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        AccountEntity that = (AccountEntity) o;

        return accountEntity.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return accountEntity.getId().hashCode();
    }
}

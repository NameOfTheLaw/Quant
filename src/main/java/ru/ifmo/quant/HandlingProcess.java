package ru.ifmo.quant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.quant.entity.AccountEntity;

/**
 * Created by andrey on 08.11.2016.
 */
public class HandlingProcess {

    //process life time = 1 hour
    public static final Long PROCESS_LIFE_TIME = 3600000l;
    private HandleState handleState;
    private AccountEntity accountEntity;
    private Long lastActiveTime;
    private ProcessContainer processContainer;

    public HandlingProcess(HandleState handleState, AccountEntity accountEntity) {
        this.handleState = handleState;
        this.accountEntity = accountEntity;
        this.lastActiveTime = System.currentTimeMillis();
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

    public HandleState getHandleState() {
        return handleState;
    }

    public void setHandleState(HandleState handleState) {
        this.handleState = handleState;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public Long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public ProcessContainer getProcessContainer() {
        return processContainer;
    }

    public void setProcessContainer(ProcessContainer processContainer) {
        this.processContainer = processContainer;
    }
}

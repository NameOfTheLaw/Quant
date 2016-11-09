package ru.ifmo.quant;

import ru.ifmo.quant.entity.AccountEntity;

/**
 * Created by andrey on 08.11.2016.
 */
public class HandlingProcess {

    private HandleState handleState;
    private AccountEntity accountEntity;

    public HandlingProcess(HandleState handleState, AccountEntity accountEntity) {
        this.handleState = handleState;
        this.accountEntity = accountEntity;
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
}

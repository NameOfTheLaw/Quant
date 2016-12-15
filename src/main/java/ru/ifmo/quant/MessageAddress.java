package ru.ifmo.quant;

/**
 * Created by andrey on 08.11.2016.
 */
public class MessageAddress {

    public static final String VK_ALIAS = "vk";
    public static final String TELEGRAM_ALIAS = "telegram";
    private String social;
    private Long key;

    public MessageAddress(String social, Long key) {
        this.social = social;
        this.key = key;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(this.getClass())) return false;

        MessageAddress messageAddress = (MessageAddress) o;
        if (!messageAddress.getKey().equals(this.getKey())) return false;
        if (!messageAddress.getSocial().equals(this.getSocial())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = social != null ? social.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}

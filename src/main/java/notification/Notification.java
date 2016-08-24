package notification;

import Entites.SmallAd;

import java.util.Date;
import java.util.List;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class Notification {
    private Date dateTime;
    private List<SmallAd> smallAdList;

    public Notification(Date dateTime, List<SmallAd> smallAdList) {
        this.dateTime = dateTime;
        this.smallAdList = smallAdList;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public List<SmallAd> getSmallAdList() {
        return smallAdList;
    }

}

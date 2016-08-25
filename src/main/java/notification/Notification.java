package notification;

import Entites.SmallAd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class Notification {
    private Date dateTime;
    private List<SmallAd> smallAdList;

    public Notification(List<SmallAd> smallAdList) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        dateFormat.format(currentDate);

        this.dateTime = currentDate;
        this.smallAdList = smallAdList;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public List<SmallAd> getSmallAdList() {
        return smallAdList;
    }

}

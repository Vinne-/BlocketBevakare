package Interfaces;

import Entites.SmallAd;

import java.util.List;

/**
 * Created by simpa2k on 2016-09-01.
 */
public interface Notifiable {

    void handleNotification(List<SmallAd> ads);

}

package com.iceblizzard.advancecombat.task;

import com.iceblizzard.advancecombat.main.AdvanceCombat;
import com.iceblizzard.advancecombat.user.UserManager;

public class AddUserTask implements Runnable {

    private final UserManager userManager = AdvanceCombat.getInstance().getUserManager();

    @Override
    public void run() {
        userManager.addAllToUserMapIfAbsent();
    }
}

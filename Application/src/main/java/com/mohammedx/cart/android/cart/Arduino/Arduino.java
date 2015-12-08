package com.mohammedx.cart.android.cart.Arduino;


import me.palazzetti.adktoolkit.AdkManager;

/**
 * Implements Arduino commands interface
 */
public class Arduino {
    public static void command(AdkManager adkManager, int command) throws NotImplementedCommand {

        switch (command) {
            case 0:
                move(adkManager);
                break;
            case 1:
                stop(adkManager);
                break;
            case 2:
                left(adkManager);
                break;
            case 3:
                right(adkManager);
                break;
            case 4:
                near(adkManager);
                break;
            case 5:
                far(adkManager);
                break;
            case 6:
                down(adkManager);
                break;
            default:
                throw new NotImplementedCommand("This command is not available");
        }
    }


    private static void move(AdkManager adkManager) {
        adkManager.write("0");
    }

    private static void stop(AdkManager adkManager) {
        adkManager.write("1");
    }

    private static void left(AdkManager adkManager) {
        adkManager.write("2");
    }

    private static void right(AdkManager adkManager) {
        adkManager.write("3");
    }

    private static void near(AdkManager adkManager) {
        adkManager.write("4");
    }

    private static void far(AdkManager adkManager) {
        adkManager.write("5");
    }

    private static void down(AdkManager adkManager) {
        adkManager.write("6");
    }
}

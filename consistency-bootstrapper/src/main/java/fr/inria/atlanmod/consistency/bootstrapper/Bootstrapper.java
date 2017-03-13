package fr.inria.atlanmod.consistency.bootstrapper;

import fr.inria.atlanmod.appa.rmi.RMIBootstrapper;

public class Bootstrapper extends RMIBootstrapper {

    public static void main(String[] args) {
        Bootstrapper b = new Bootstrapper();

        b.start();
    }
}

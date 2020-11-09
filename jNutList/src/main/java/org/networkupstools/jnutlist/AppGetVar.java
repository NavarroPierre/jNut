/* AppList.java

   Copyright (C) 2011 Eaton

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package org.networkupstools.jnutlist;

import org.networkupstools.jnut.Client;
import org.networkupstools.jnut.Device;
import org.networkupstools.jnut.Variable;

public class AppGetVar {

    public static void main(String[] args) {
        System.setProperty("log4j.rootLogger", "DEBUG");

        String host = args.length >= 1 ? args[0] : "10.130.34.119";
        int port = args.length >= 2 ? Integer.valueOf(args[1]).intValue() : 3493;
        String login = args.length >= 3 ? args[2] : "";
        String pass = args.length >= 4 ? args[3] : "";

        System.out.println("jNutApp connecting to " + login + ":" + pass + "@" + host + ":" + port);

        Client client = new Client();

        try {
            client.setHost(host);
            client.setPort(port);
            client.setLogin(login);
            client.setPasswd(pass);

            client.connect();
            System.out.println("getDevice");
            Device device = client.getDevice("ups-7");
            System.out.println("getVariable");
            Variable variable = device.getVariable("device.model");
            System.out.println("getValue");
            String value = variable.getValue();
            System.out.println("value: ".concat(value));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

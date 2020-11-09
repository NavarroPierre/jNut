/* StringLineSocket.java

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
package org.networkupstools.jnut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Class representing a socket, internally used to communicate with UPSD.
 * Abstract some stream mechanisms.
 *
 * @author <a href="mailto:EmilienKia@eaton.com">Emilien Kia</a>
 */
class StringLineSocket {

    /**
     * Real internal TCP socket.
     */
    Socket socket = null;

    /**
     * Writer to the socket.
     */
    private OutputStreamWriter writer = null;

    /**
     * Reader from the socket.
     */
    private BufferedReader reader = null;

    /**
     * Create a new line socket.
     */
    public StringLineSocket() {

    }

    /**
     * Create a new line socket and connect it.
     *
     * @param host Host to connect to
     * @param port Port to connect to
     * @throws IOException Exception on socket
     */
    public StringLineSocket(String host, int port) throws IOException {
        connect(host, port);
    }

    /**
     * Connect a new line socket.
     *
     * @param host Host to connect to
     * @param port Port to connect to
     * @throws IOException Exception on socket creation
     */
    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        if (socket != null) {
            reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            writer = new OutputStreamWriter(socket.getOutputStream());
        }
    }

    /**
     * Close the socket.
     */
    public void close() throws IOException {
        if (socket != null) {
            writer.close();
            reader.close();
            socket.close();
            socket = null;
            writer = null;
            reader = null;
        }
    }

    /**
     * Test if the socket is connected.
     *
     * @return True if connected.
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    /**
     * Write a line follow by a '\n' character.
     *
     * @param line
     * @throws IOException Exception on write
     */
    public void write(String line) throws IOException {
        if (isConnected()) {
            writer.write(line + "\n");
            writer.flush();
        } else {
            System.err.println("Not connected (write)");
        }
    }

    /**
     * Read a line terminated by a '\n'.
     *
     * @return The line without the ending '\n'
     * @throws IOException Exception on read line
     */
    public String read() throws IOException {
        String res = "";
        if (isConnected()) {
            res = reader.readLine();
        } else {
            System.err.println("Not connected (read)");
        }

        return res;
    }

}

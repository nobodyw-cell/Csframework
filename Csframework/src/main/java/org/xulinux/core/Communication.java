package org.xulinux.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * //TODO
 *
 * @Author wfh
 * @Date 2022/1/2 下午2:16
 */
public abstract class Communication implements Runnable{
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private volatile boolean goon;

    public abstract void dealAbnormalDrop();
    public abstract void dealMessage(String message);

    public Communication(Socket socket) {
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());

            startUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        try {
            this.dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startUp() {
        this.goon = true;
        new Thread(this).start();
    }

    public void terminal() {
        if (this.goon == false) {
            return;
        }

        this.goon = false;

        if (this.dis != null) {
            try {
                this.dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.dis = null;
            }
        }

        if (this.dos != null) {
            try {
                this.dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.dos = null;
            }
        }

        if (this.socket != null && !this.socket.isClosed()) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.socket = null;
            }
        }
    }

    @Override
    public void run() {
        while (this.goon = true) {
            try {
                String message = this.dis.readUTF();
                dealMessage(message);
            } catch (IOException e) {
                if (this.goon == true) {
                    dealAbnormalDrop();
                }
            }
        }
    }
}

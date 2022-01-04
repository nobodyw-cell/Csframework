package org.xulinux.core;

import org.xulinux.util.Util;

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
    public abstract void dealMessage(NetMessage message);

    public Communication(Socket socket) {
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());

            startUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(NetMessage message) {
        try {
            this.dos.writeUTF(Util.gson.toJson(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startUp() {
        this.goon = true;
        new Thread(this).start();
    }

    public void close() {
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
                dealMessage(Util.gson.fromJson(message,NetMessage.class));
            } catch (IOException e) {
                if (this.goon == true) {
                    dealAbnormalDrop();
                }
            }
        }
    }
}

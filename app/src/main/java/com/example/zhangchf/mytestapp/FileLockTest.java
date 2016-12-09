package com.example.zhangchf.mytestapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zhangchf on 12/28/15.
 */
public class FileLockTest {

    private static final String TAG = FileLockTest.class.getSimpleName();

    File testFile;

    Runnable r = new Runnable() {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();

            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(testFile, true);

                System.out.println("locking... " + threadName);
                fos.getChannel().lock();
                System.out.println("locked " + threadName);
                String test1 = "This is a test string 1," + threadName + "\n";
                fos.write(test1.getBytes());
                Thread.sleep(3000);
                String test2 = "This is a test string 2," + threadName + "\n";
                fos.write(test2.getBytes());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(fos != null){
                    try {
                        System.out.println("close outputstream " + threadName);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public FileLockTest(Context context) {

        try {
            testFile = new File(context.getCacheDir(), "test.txt");
            testFile.delete();
            testFile.createNewFile();

            Thread t1 = new Thread(r);
            Thread t2 = new Thread(r);
            t1.setName("Thread 1");
            t2.setName("Thread 2");
            t1.start();
            t2.start();

            t1.join();
            t2.join();

            BufferedReader br = new BufferedReader(new FileReader(testFile));

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }


            File newFile = new File(context.getCacheDir(), "new.txt");
            newFile.createNewFile();


            boolean result = testFile.renameTo(newFile);
            Log.i(TAG, "rename result:" + result);


            result = testFile.renameTo(newFile);
            Log.i(TAG, "rename result:" + result);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

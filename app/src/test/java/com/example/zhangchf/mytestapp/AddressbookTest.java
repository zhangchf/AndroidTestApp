package com.example.zhangchf.mytestapp;

import com.example.zhangchf.mytestapp.protobuf.Addressbook;

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhangchf on 28/04/2017.
 */

public class AddressbookTest {

    @Test
    public void testAddressbook() throws IOException {
        Addressbook.Person.Builder personBuilder = Addressbook.Person.newBuilder();
        personBuilder.setId(100).setEmail("test@test.com").setName("testPerson");
        Addressbook.Person person = personBuilder.build();

        File f = new File("test.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(f);
        person.writeTo(fos);

        Addressbook.Person personRead = Addressbook.Person.parseFrom(new FileInputStream(f));

        System.out.println("person read:" + personRead.toString());

        Assert.assertEquals(person.getEmail(), personRead.getEmail());
        Assert.assertEquals(person.getId(), personRead.getId());
        Assert.assertEquals(person.getName(), person.getName());
    }
}

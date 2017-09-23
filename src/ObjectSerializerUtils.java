
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lipec
 */
public class ObjectSerializerUtils {

    private static ObjectSerializerUtils instance;

    public static ObjectSerializerUtils getInstane() {
        if (instance == null) {
            instance = new ObjectSerializerUtils();
        }
        return instance;
    }

    public byte[] serialize(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    public Object deserialize(byte[] object) {
        ByteArrayInputStream bis = new ByteArrayInputStream(object);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}

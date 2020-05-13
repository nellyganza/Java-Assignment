/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.dao;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author NISHIMWE Elyse
 */
public class IOClass {
    public String encoding(String password){
        Base64.Encoder en = Base64.getEncoder();
        return en.encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
    public String deencoding(String password){
        Base64.Decoder en = Base64.getDecoder();
        return new String(en.decode(password)); 
    }
}

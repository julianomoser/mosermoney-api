package br.com.moser.mosermoney.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Juliano Moser
 */
public class GeradorSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("@ngul@r0"));
    }
}

package Util;

/*
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.EmailException;
 */
import java.util.Random;
import org.apache.commons.mail.*;

public class EnviaEmail {

    private static String host = "smtp.gmail.com";
    private static String sslPort = "465";
    private static String emailFrom = "ricardo@scc4.com.br";
    private static String senhaFrom = "1s2c3c4.2010";
    private static String nomeFrom = "Portal Postal";

    public static String geraSenha(int size) {
        char c;
        char[] chars = new char[52]; //52, pois o alfabeto tem 26 letras. 26x2= 52 (maiúsculas e minúsculas)
        int i = 0;

        String pass = "";
        //Nestes dois loops, eu capturo os caracteres
        for (c = 'a'; c <= 'z'; c++) {
            chars[i] = c;
            i++;
        }
        for (c = 'A'; c <= 'Z'; c++) {
            chars[i] = c;
            i++;
        }
        Random rand = new Random();
        int pos;
        for (pos = 0; pos < size; pos++) {
            if (rand.nextBoolean()) {
                pass += rand.nextInt(9);
            } else {
                pass += chars[rand.nextInt(51)];
            }
        }

        return pass;
    }

    public static String SendMailEsqueceuSenhaHoito(String nome, String login, String senha, String emailPara) throws EmailException {
        if (emailPara != null && !emailPara.trim().equals("") && emailPara.contains("@") && emailPara.length() > 6) {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(host);
            email.setSslSmtpPort(sslPort);
            email.setSSL(true);
            email.setFrom(emailFrom, nomeFrom);
            email.addTo(emailPara, nome);
            email.setHtmlMsg(EnviaEmail.mensagemEsqueceuSenhaHoito(nome, login, senha));
            email.setSubject(nomeFrom + " - Nova Senha de Acesso.");
            email.setAuthentication(emailFrom, senhaFrom);
            email.send();

            return senha;
        }
        return "";
    }

    private static String mensagemEsqueceuSenhaHoito(String nome, String login, String senha) {
        return "<html xmlns=' http://www.w3.org/1999/xhtml' dir='ltr' lang='br'>"
                + "<head>"
                + "<meta http-equiv='Content-Type' content='text/html'; charset='UTF-8'/>"
                + "<body style='font-family:Verdana;'>"
                + "<span style='color:#0073a1;font-weight:bold; font-size:14px;'>Olá " + nome + ",<br>O Portal Postal gerou uma nova senha para você!</span><br><br>"
                + "<b>Login:</b> <span style='color:#00F;'>" + login + "</span><br>"
                + "<b>Senha:</b> <span style='color:#00F;'>" + senha + "</span><br><br>"
                + "Para acessar nosso site clique na URL abaixo<br>"
                + "<a style='cursor:pointer' href= 'http://www.portalpostal.com.br'>Clique aqui para acessar o site</a><br><br>"
                + "<span style='color:#C9321D;'>Desenvolvido por</span> <b><a href='http://www.scc4.com.br' style='color:#C9321D; text-decoration:none;'><btext-decoration:none;'>SCC4 Ltda</b></a>"
                + "</body>"
                + "</html>";
    }
}

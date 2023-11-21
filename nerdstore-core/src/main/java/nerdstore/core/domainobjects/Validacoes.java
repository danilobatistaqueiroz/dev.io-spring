package nerdstore.core.domainobjects;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacoes {
	
    public static void validarSeIgual(Object object1, Object object2, String mensagem) throws DomainException {
        if (object1.equals(object2)) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeDiferente(Object object1, Object object2, String mensagem) throws DomainException {
        if (!object1.equals(object2)) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeDiferente(String pattern, String valor, String mensagem) throws DomainException {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(valor);
        boolean found = m.find();
        if(found==false) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarTamanho(String valor, int maximo, String mensagem) throws DomainException {
        var length = valor.trim().length();
        if (length > maximo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarTamanho(String valor, int minimo, int maximo, String mensagem) throws DomainException {
        var length = valor.trim().length();
        if (length < minimo || length > maximo) {
            throw new DomainException(mensagem);
        }
    }
    
    public static void validarSeVazio(String valor, String mensagem) throws DomainException {
        if (valor == null || valor.trim().length() == 0) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeNulo(Object object1, String mensagem) throws DomainException {
        if (object1 == null) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarMinimoMaximo(double valor, double minimo, double maximo, String mensagem) throws DomainException {
        if (valor < minimo || valor > maximo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarMinimoMaximo(float valor, float minimo, float maximo, String mensagem) throws DomainException {
        if (valor < minimo || valor > maximo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarMinimoMaximo(int valor, int minimo, int maximo, String mensagem) throws DomainException {
        if (valor < minimo || valor > maximo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarMinimoMaximo(long valor, long minimo, long maximo, String mensagem) throws DomainException {
        if (valor < minimo || valor > maximo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarMinimoMaximo(BigDecimal valor, BigDecimal minimo, BigDecimal maximo, String mensagem) throws DomainException {
        if (valor==null || valor.compareTo(minimo)==-1 || valor.compareTo(maximo)==1) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeMenorQue(long valor, long minimo, String mensagem) throws DomainException {
        if (valor < minimo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeMenorQue(double valor, double minimo, String mensagem) throws DomainException {
        if (valor < minimo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeMenorQue(BigDecimal valor, BigDecimal minimo, String mensagem) throws DomainException {
        if (valor==null || valor.compareTo(minimo)==-1) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeMenorQue(int valor, int minimo, String mensagem) throws DomainException {
        if (valor < minimo) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeFalso(Boolean boolvalor, String mensagem) throws DomainException {
        if (!boolvalor) {
            throw new DomainException(mensagem);
        }
    }

    public static void validarSeVerdadeiro(Boolean boolvalor, String mensagem) throws DomainException {
        if (boolvalor) {
            throw new DomainException(mensagem);
        }
    }
    
}
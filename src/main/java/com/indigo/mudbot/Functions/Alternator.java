package com.example.hello;

public class Alternator {
    public static String owoify(String toOwO) {
        String bigowo = toOwO
                .replaceAll("na", "nya")
                .replaceAll("for", "fow")
                .replaceAll("oo", "owo")
                .replaceAll("fr", "fw")
                .replaceAll("or", "ow")
                .replaceAll("ull", "uww")
                .replaceAll("ur", "uw")
                .replaceAll("oy", "oi")
                .replaceAll("ght$", "te")
                .replaceAll("\\ban\\b", "a")
                .replaceAll("ua", "wa")
                .replaceAll("er", "ew")
                .replaceAll("\\bth\\b", "f")
                .replaceAll("re", "we")
                .replaceAll("tr", "tw")
                .replaceAll("ll", "ww")
                .replaceAll("lo", "wo")
                .replaceAll("ra", "wa")
                .replaceAll("le", "we")
                .replaceAll("ro", "wo")
                .replaceAll("v", "f");


        return bigowo;
    }
}

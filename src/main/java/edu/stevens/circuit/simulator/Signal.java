package edu.stevens.circuit.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * An enum which represents the possible states of a signal
 * 
 * Necessary to be able to deal with feedback loops, as without a trinary truth system it's
 * incredibly hard to deal with unknown signals.
 */
public enum Signal {
    HI('1'), LO('0'), X('X');

    private final char type;

    Signal(char c) {
        this.type = c;
    }

    public Signal invert() {
        if (type == '1') {
            return LO;
        }
        if (type == '0') {
            return HI;
        }
        return X;
    }

    public static Signal fromChar(char c) throws MalformedSignal {
        if (c == '1') {
            return HI;
        }
        if (c == '0') {
            return LO;
        }
        if (c == 'X' || c == 'x') {
            return X;
        }
        throw new MalformedSignal(c);
    }

    public static List<Signal> fromString(String inputs) throws MalformedSignal {
        List<Signal> list = new ArrayList<>();

        for (char c : inputs.toCharArray()) {
            if (!"01xX \t".contains(Character.toString(c))) {
                throw new MalformedSignal(c);
            }
            if (c == ' ' || c == '\t') {
                continue;
            }
            if (c == '1') {
                list.add(HI);
            }
            if (c == '0') {
                list.add(LO);
            }
            if (c == 'X' || c == 'x') {
                list.add(X);
            }
        }

        return list;
    }

    @Override
    public String toString() {
        return Character.toString(type);
    }

    public static String toString(List<Signal> sig) {
        StringBuilder b = new StringBuilder();
        for (Signal s : sig) {
            b.append(s.toString());
        }

        return b.toString();
    }

}

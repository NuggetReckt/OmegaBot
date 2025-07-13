package fr.nuggetreckt.omegabot.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("Error: Member not found!");
    }
}

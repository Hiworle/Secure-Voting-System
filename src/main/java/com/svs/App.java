package com.svs;

public class App {
    public static void main(String[] args) throws Exception {
        // Test test = new Test();
        // test.run();
        Candidate candidate = new Candidate(3);
        candidate.name[0] = "Alice";
        candidate.name[1] = "Bob";
        candidate.name[2] = "Cat";
        String voteMsg[] = { "1", "0", "1" };

        String in = Formater.toCreate("1", candidate, voteMsg);
        try {
            MyClient.run(in);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

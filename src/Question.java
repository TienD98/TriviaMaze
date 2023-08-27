public class Question {
    String question;
    int type;
    String answer;

    public Question(String question, String answer, int type) {
        this.question = question;
        this.type = type;
        this.answer = answer;
    }

    @Override
    public String toString(){
        String s=question+"\n"+"Answer: "+answer;
        return s;
    }

}

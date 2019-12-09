public class Contact {

    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String toString(){
        return name + ":" + number;
    }

    public String formattedString(){
        StringBuilder name = new StringBuilder(this.name);
        int amountOfSpace = 15 - name.length();
        String formattedNum = "| ";
        if(this.number.length() == 10){
            formattedNum = this.number.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
            formattedNum += " |";
        }else if(this.number.length() == 7){
            formattedNum = this.number.replaceFirst("(\\d{3})(\\d+)", "$1-$2");
            formattedNum += "       |";
        }
        for (int i = 0; i <= amountOfSpace; i++) {
            name.append(" ");
        }
        return name + " | " + formattedNum;
    }


}

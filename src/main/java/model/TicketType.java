package model;

public enum TicketType {
    standard,
    VIP;

    @Override
    public String toString() {
        if(this.equals(TicketType.standard)){
            return "Стандартний";
        }else{
            return "VIP";
        }
    }
}

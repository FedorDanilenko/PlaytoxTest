package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
class Account {
    private final String ID;
    private int money;

    public void withdraw(int amount) {
        money -= amount;
    }

    public void deposit(int amount) {
        money += amount;
    }
}

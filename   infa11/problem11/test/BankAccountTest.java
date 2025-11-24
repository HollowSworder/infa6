import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import java.io.StringReader;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("A88", 1000.0);
    }

    @Test
    void testGetBalance() {
        account.deposit(500);
        assertEquals(1500, account.getBalance());
    }

    @Test
    void testGetAccountNumber() {
        assertEquals("A88", account.getAccountNumber());
    }

    @Test
    void testIsActive() {
        assertTrue(account.isActive());
        account.deactivate();
        assertFalse(account.isActive());
    }

    @Test
    void testCalculateMonthlyInterestActive() {
        double interest = account.calculateMonthlyInterest(12); 
        assertEquals(1000 * (12.0 / 12) / 100, interest);
    }

    @Test
    
    void testCalculateMonthlyInterestInactive() {
        account.deactivate();
        assertEquals(0.0, account.calculateMonthlyInterest(10));
    }

    @Test
    
    void testCalculateInterestRates() {
        assertEquals(1000 * (9.0 / 12) / 100, account.calculateMonthlyInterest(9));
        assertEquals(1000 * (18.0 / 12) / 100, account.calculateMonthlyInterest(18));
    }

    @Test
    void testConstructorNullAccountNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> new BankAccount(null, 100)
        );
    }

    @Test
    void testConstructorNegativeBalance() {
        assertThrows(IllegalArgumentException.class,
                () -> new BankAccount("AAA", -5)
        );
    }

    @Test
    void testDepositInactive() {
        account.deactivate();
        assertThrows(IllegalStateException.class,
                () -> account.deposit(100)
        );
    }

    @Test
    void testDepositNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(-10)
        );
    }

    @Test
    void testWithdrawInsufficientFunds() {
        assertThrows(InsufficientFundsException.class,
                () -> account.withdraw(5000)
        );
    }

    @Test
    void testWithdrawNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(-100)
        );
    }

    @Test
    void testTransferNullAccount() {
        assertThrows(IllegalArgumentException.class,
                () -> account.transfer(null, 100)
        );
    }

    @Test
    void testTransferToInactive() {
        BankAccount target = new BankAccount("TGT", 100);
        target.deactivate();
        assertThrows(IllegalStateException.class,
                () -> account.transfer(target, 50)
        );
    }

    @Test
    void testDepositValid() {
        assertDoesNotThrow(() -> account.deposit(100));
    }

    @Test
    void testWithdrawValid() {
        assertDoesNotThrow(() -> account.withdraw(100));
    }

    @Test
    void testTransferValid() {
        BankAccount target = new BankAccount("TGT", 200);
        assertDoesNotThrow(() -> account.transfer(target, 300));
    }

    @Test
    void testInterestValid() {
        assertDoesNotThrow(() -> account.calculateMonthlyInterest(10));
    }

    @Test
    void testReadFromReaderValid() throws IOException {
        Reader r = new StringReader("ewqe22,500,true");
        BufferedReader br = new BufferedReader(r);

        BankAccount acc = BankAccount.readFromReader(br);

        assertEquals("ewqe22", acc.getAccountNumber());
        assertEquals(500, acc.getBalance());
        assertTrue(acc.isActive());
    }

    @Test
    void testReadFromReaderInactive() throws IOException {
        Reader r = new StringReader("qwe01,200,false");
        BufferedReader br = new BufferedReader(r);

        BankAccount acc = BankAccount.readFromReader(br);

        assertFalse(acc.isActive());
    }

    @Test
    void testReadFromReaderInvalidFormat() {
        Reader r = new StringReader("BAD DATA");
        BufferedReader br = new BufferedReader(r);

        assertThrows(IOException.class,
                () -> BankAccount.readFromReader(br)
        );
    }

    @Test
    void testReadFromReaderEmpty() {
        Reader r = new StringReader("");
        BufferedReader br = new BufferedReader(r);

        assertThrows(IOException.class,
                () -> BankAccount.readFromReader(br)
        );
    }

}
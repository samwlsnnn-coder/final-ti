public class Transaction {
    // Private attributes (Encapsulation)
    private String transactionId;
    private Member member;
    private Book book;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private int daysLate;
    private double lateFee;

    // Static variables
    private static int totalTransactions = 0;
    private static int transactionCounter = 0;
    private static final double LATE_FEE_PER_DAY = 2000.0;

    // Constructor
    public Transaction(Member member, Book book, String borrowDate, int borrowDurationDays) {
        if (isValidDate(borrowDate)) {
            this.transactionId = generateTransactionId();
            this.member = member;
            this.book = book;
            this.borrowDate = borrowDate;
            this.dueDate = calculateDueDate(borrowDate, borrowDurationDays);
            this.returnDate = null;
            this.daysLate = 0;
            this.lateFee = 0;
            totalTransactions++;
        } else {
            System.out.println("✗ Error: Format tanggal tidak valid (DD-MM-YYYY)");
            this.transactionId = "INVALID";
        }
    }

    // Generate auto transactionId
    private String generateTransactionId() {
        transactionCounter++;
        return String.format("TRX%03d", transactionCounter);
    }

    // Validate date format (DD-MM-YYYY)
    private static boolean isValidDate(String date) {
        if (date == null || !date.matches("\\d{2}-\\d{2}-\\d{4}")) return false;

        String[] parts = date.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1900 && year <= 2025;
    }

    // Calculate due date
    private String calculateDueDate(String borrowDate, int days) {
        String[] parts = borrowDate.split("-");
        int day = Integer.parseInt(parts[0]) + days;
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        // Simple calculation (not accounting for month boundaries perfectly)
        while (day > 31) {
            day -= 31;
            month++;
        }
        while (month > 12) {
            month -= 12;
            year++;
        }

        return String.format("%02d-%02d-%04d", day, month, year);
    }

    // Convert date to day number for comparison
    private static int dateToDay(String date) {
        String[] parts = date.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return year * 10000 + month * 100 + day;
    }

    // Calculate days between two dates
    private static int daysBetween(String date1, String date2) {
        int d1 = dateToDay(date1);
        int d2 = dateToDay(date2);
        return Math.abs(d2 - d1) / 100; // Simplified calculation
    }

    // Process return
    public void processReturn(String returnDate) {
        if (!isValidDate(returnDate)) {
            System.out.println("✗ Error: Format tanggal tidak valid (DD-MM-YYYY)");
            return;
        }

        if (dateToDay(returnDate) < dateToDay(borrowDate)) {
            System.out.println("✗ Error: Tanggal kembali tidak boleh sebelum tanggal pinjam");
            return;
        }

        this.returnDate = returnDate;

        // Calculate days late
        if (dateToDay(returnDate) > dateToDay(dueDate)) {
            this.daysLate = daysBetween(dueDate, returnDate);
        } else {
            this.daysLate = 0;
        }

        // Calculate late fee
        calculateLateFee();

        // Return the book
        book.returnBook();
    }

    // Calculate late fee
    public void calculateLateFee() {
        if (daysLate > 0) {
            double discount = 1 - member.getMembershipDiscount();
            this.lateFee = daysLate * LATE_FEE_PER_DAY * discount;
        } else {
            this.lateFee = 0;
        }
    }


    // Check if overdue
    public boolean isOverdue(String currentDate) {
        if (returnDate != null) return false; // Already returned
        return dateToDay(currentDate) > dateToDay(dueDate);
    }

    // Get transaction status
    public String getTransactionStatus() {
        if (returnDate != null) {
            return "Selesai";
        }

        // Assuming current date is 05-12-2025 for demo purposes
        String currentDate = "05-12-2025";
        if (isOverdue(currentDate)) {
            return "Terlambat";
        }

        return "Aktif";
    }

    // Static method - Get total transactions
    public static int getTotalTransactions() {
        return totalTransactions;
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public Member getMember() { return member; }
    public Book getBook() { return book; }
    public String getBorrowDate() { return borrowDate; }
    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public int getDaysLate() { return daysLate; }
    public double getLateFee() { return lateFee; }
}
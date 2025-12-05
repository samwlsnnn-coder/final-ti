/**
 * CLASS BOOK
 * Merepresentasikan buku di perpustakaan
 * Encapsulation, Constructor, Instance & Static variables, Getters & Setters
 */
public class Book {
    // ===== PRIVATE ATTRIBUTES (Encapsulation) =====
    private String bookId;
    private String title;
    private String author;
    private String category;
    private int publicationYear;
    private boolean isAvailable;
    private int totalCopies;
    private int availableCopies;

    // ===== STATIC VARIABLES =====
    private static int totalBooks = 0;
    private static int bookCounter = 0;

    // ===== VALID CATEGORIES =====
    private static final String[] VALID_CATEGORIES = {
            "Fiction", "Non-Fiction", "Science", "Technology", "History"
    };

    // ===== DEFAULT CONSTRUCTOR =====
    /**
     * Default Constructor - Generate auto bookId
     */
    public Book() {
        this.bookId = "BK" + String.format("%03d", ++bookCounter);
        this.title = "";
        this.author = "";
        this.category = "Fiction";
        this.publicationYear = 2025;
        this.isAvailable = true;
        this.totalCopies = 1;
        this.availableCopies = 1;
        totalBooks++;
    }

    // ===== PARAMETERIZED CONSTRUCTOR =====
    /**
     * Parameterized Constructor dengan validasi
     */
    public Book(String title, String author, String category,
                int publicationYear, int totalCopies) {
        // Validasi
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException("Error: Judul buku tidak boleh kosong");
        }
        if (!isValidAuthor(author)) {
            throw new IllegalArgumentException("Error: Nama penulis tidak boleh kosong");
        }
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException("Error: Kategori harus Fiction/Non-Fiction/Science/Technology/History");
        }
        if (!isValidPublicationYear(publicationYear)) {
            throw new IllegalArgumentException("Error: Tahun terbit tidak valid (1900-2025)");
        }
        if (!isValidCopies(totalCopies)) {
            throw new IllegalArgumentException("Error: Total copies harus >= 1");
        }

        // Set attributes
        this.bookId = "BK" + String.format("%03d", ++bookCounter);
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.isAvailable = totalCopies > 0;
        totalBooks++;
    }

    // ===== VALIDATION METHODS =====
    /**
     * Validasi judul tidak kosong
     */
    private boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    /**
     * Validasi author tidak kosong
     */
    private boolean isValidAuthor(String author) {
        return author != null && !author.trim().isEmpty();
    }

    /**
     * Validasi kategori
     */
    private boolean isValidCategory(String category) {
        for (String valid : VALID_CATEGORIES) {
            if (valid.equals(category)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validasi tahun terbit (1900-2025)
     */
    private boolean isValidPublicationYear(int year) {
        return year >= 1900 && year <= 2025;
    }

    /**
     * Validasi total copies
     */
    private boolean isValidCopies(int copies) {
        return copies >= 1;
    }

    /**
     * Validasi availableCopies
     */
    private boolean isValidAvailableCopies(int copies) {
        return copies >= 0 && copies <= totalCopies;
    }

    // ===== BUSINESS LOGIC METHODS =====
    /**
     * Tampilkan info lengkap buku
     */
    public void displayBookInfo() {
        System.out.println("[" + bookId + "] " + title);
        System.out.println("Penulis       : " + author);
        System.out.println("Kategori      : " + category);
        System.out.println("Tahun Terbit  : " + publicationYear);
        System.out.println("Umur Buku     : " + getBookAge() + " tahun");
        System.out.println("Total Copy    : " + totalCopies + " eksemplar");
        System.out.println("Tersedia      : " + availableCopies + " eksemplar | Status: " +
                getAvailabilityStatus() + (isNewRelease() ? " [NEW RELEASE]" : ""));
    }

    /**
     * Pinjam buku - kurangi availableCopies jika > 0
     * Return true jika berhasil
     */
    public boolean borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            if (availableCopies == 0) {
                isAvailable = false;
            }
            return true;
        }
        return false;
    }

    /**
     * Kembalikan buku - tambah availableCopies
     */
    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            if (availableCopies > 0) {
                isAvailable = true;
            }
        }
    }

    /**
     * Hitung umur buku (2025 - publicationYear)
     */
    public int getBookAge() {
        return 2025 - publicationYear;
    }

    /**
     * Check apakah buku terbit dalam 2 tahun terakhir (new release)
     */
    public boolean isNewRelease() {
        return getBookAge() <= 2;
    }

    /**
     * Get status ketersediaan
     * availableCopies > 5: "Banyak Tersedia"
     * availableCopies 1-5: "Terbatas"
     * availableCopies = 0: "Tidak Tersedia"
     */
    public String getAvailabilityStatus() {
        if (availableCopies > 5) {
            return "Banyak Tersedia ✓";
        } else if (availableCopies >= 1 && availableCopies <= 5) {
            return "Terbatas ⚠";
        } else {
            return "Tidak Tersedia ✗";
        }
    }

    // ===== STATIC METHODS =====
    /**
     * Return total buku (Static method)
     */
    public static int getTotalBooks() {
        return totalBooks;
    }

    // ===== GETTERS & SETTERS =====
    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (isValidTitle(title)) {
            this.title = title;
        } else {
            System.out.println("✗ Error: Judul buku tidak boleh kosong");
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (isValidAuthor(author)) {
            this.author = author;
        } else {
            System.out.println("✗ Error: Nama penulis tidak boleh kosong");
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (isValidCategory(category)) {
            this.category = category;
        } else {
            System.out.println("✗ Error: Kategori harus Fiction/Non-Fiction/Science/Technology/History");
        }
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        if (isValidPublicationYear(publicationYear)) {
            this.publicationYear = publicationYear;
        } else {
            System.out.println("✗ Error: Tahun terbit tidak valid (1900-2025)");
        }
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        this.isAvailable = available;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        if (isValidCopies(totalCopies)) {
            this.totalCopies = totalCopies;
        } else {
            System.out.println("✗ Error: Total copies harus >= 1");
        }
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if (isValidAvailableCopies(availableCopies)) {
            this.availableCopies = availableCopies;
            this.isAvailable = availableCopies > 0;
        } else {
            System.out.println("✗ Error: availableCopies harus 0-" + totalCopies);

        }}
}
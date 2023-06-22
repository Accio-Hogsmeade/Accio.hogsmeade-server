package accio.hogsmeade.store.common;

public enum FilePath {

    BOARD("member/board/"), STORE("store/profile/"), ITEM("store/item/"), REVIEW("store/review/");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

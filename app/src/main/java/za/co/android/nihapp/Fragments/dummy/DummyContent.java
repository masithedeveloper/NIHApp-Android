package za.co.android.nihapp.Fragments.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.android.nihapp.Model.PersonModel;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PersonModel> ITEMS = new ArrayList<PersonModel>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Long, PersonModel> ITEM_MAP = new HashMap<Long, PersonModel>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(PersonModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getPerId(), item);
    }

    private static PersonModel createDummyItem(int position) {
        return new PersonModel(); // this must be an overloaded contractor
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}

package rogue.components.traits;

import com.badlogic.ashley.core.Component;

// Marks if an object can have items stored in it
// Note: its not enough to just have an inventory, as for example an enemy may have an inventory but we dont want
//       player to store items in it
public class CanStoreItemsInComponent implements Component {
}

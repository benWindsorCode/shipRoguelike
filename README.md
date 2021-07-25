# Ship Roguelike
A seafaring ship roguelike.

# Run
1. Import as gradle project
2. run rogue.run.RogueLikeMain

# Code Tour
Broken down by java modules:
- rogue.ai = houses all AI, this deals with onEnter and nextMove for both the player and the non player controlled entities in the game
- rogue.components = the components of the ECS pattern, they function as both data stores and events to be processed by systems
- rogue.crafting = crafting classes, recipes and recipe books to group recipes
- rogue.entities = the entities of the ECS pattern, groups together and initialises components. Each entity class has a unique EntityId
- rogue.environment = world generation code. The world grid is a 2D array of entities, on top of which other generation is applied
- rogue.factories = factories to create various objects for both the ECS pattern and tiles/entities/recipes etc.
- rogue.familygeneration = procedural family generation code, to create a family tree
- rogue.loot = loot table creation and loot drop logic
- rogue.render = the render grid, which is written to screen each update
- rogue.run = RogueLikeMain, with the Main function in to run the game
- rogue.screens = various game screens. The main one being PlayScreen
- rogue.systems = the systems of the ECS pattern, to process each tick all components + updates (remember components also act as events)
- rogue.util = utilities to create various objects and test conditions such as isLand etc.



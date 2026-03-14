# Dante's Inferno — Java 2D RPG

A top-down 2D RPG game built in Java using Swing and AWT, inspired by Dante Alighieri's *Divine Comedy*. The player traverses three worlds — Hell (Inferno), Purgatory (Purgatoriu), and Paradise (Paradis) — making moral choices that affect their karma and determine the final outcome of the game.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [Package Descriptions](#package-descriptions)
  - [main](#main)
  - [Entity](#entity)
  - [Harta (Map)](#harta-map)
  - [Obiecte (Objects)](#obiecte-objects)
  - [Camera](#camera)
  - [Collision](#collision)
  - [DatabaseManager](#databasemanager)
- [Design Patterns Used](#design-patterns-used)
- [Game Mechanics](#game-mechanics)
- [Controls](#controls)
- [Save System](#save-system)
- [Database](#database)
- [Requirements & Running the Project](#requirements--running-the-project)
- [Resource Structure](#resource-structure)

---

## Project Overview

The game is a multi-level RPG where the player character (Alvie) moves through three distinct worlds, each with unique maps, NPCs, enemies, and items. Dialogue choices with NPCs award or deduct karma points. The final ending depends on whether the player's karma is positive or negative at the end of Level 3.

**Key features:**
- Three fully tiled maps loaded from CSV layer files
- NPC dialogue system with branching moral choices
- Inventory system with potions, keys, and collectibles
- Karma and health tracking with a live HUD
- Enemy AI (Orcs in Hell, Ghosts in Purgatory)
- In-game pause menu with save/load system (SQLite, 7 slots)
- Leaderboard stored in the database

---

## Project Structure

```
project-root/
├── main/
│   ├── Main.java
│   ├── MainMenu.java
│   ├── GamePanel.java
│   ├── KeyHandler.java
│   ├── Inventory.java
│   ├── LiveHealthBar.java
│   ├── ScreenManager.java
│   └── inGameMenu/
│       └── MenuIngame.java
├── Entity/
│   ├── Entity.java
│   ├── Player.java
│   ├── Orco.java
│   ├── Ghost.java
│   ├── Mike.java
│   ├── Son.java
│   ├── Sot.java
│   ├── Sotie.java
│   ├── Seller.java
│   ├── OracolInfern.java
│   ├── OracolParadis.java
│   ├── Characterr.java (interface)
│   ├── MainCharacter.java (interface)
│   ├── AggroCharacter.java (interface)
│   ├── NPC.java (interface)
│   ├── PlayerObserver.java (interface)
│   ├── CharacterFactory.java (abstract)
│   ├── MainCharacterFactory.java
│   ├── NPCFactory.java
│   └── AggroCharacterFactory.java
├── Harta/
│   ├── InterfaceHarta.java
│   ├── HartaInfern.java
│   ├── HartaPurgatoriu.java
│   └── HartaParadis.java
├── Obiecte/
│   ├── ObjInterface.java
│   ├── PotionInterface.java
│   ├── MoneyInterface.java
│   ├── HealthPotion.java
│   ├── SpeedPotion.java
│   ├── InutilPotion.java
│   ├── KeyOne.java
│   ├── KeyTwo.java
│   ├── KeyThree.java
│   ├── Ring.java
│   ├── Skull.java
│   ├── Money.java
│   └── MoneyManager.java
├── Camera/
│   └── Camera.java
├── Collision/
│   └── CollisionCheck.java
├── DatabaseManager/
│   ├── DataBaseSaveSlotManager.java
│   └── SaveData.java
└── resources/
    ├── tileset/
    ├── layer/
    ├── Sprites/
    └── database/
```

---

## Package Descriptions

### `main`

The core package that wires everything together and manages the game loop.

| Class | Responsibility |
|---|---|
| `Main` | Entry point. Launches the `MainMenu` on the Swing thread. |
| `MainMenu` | Swing-based main menu with Start, Load Game, Leaderboard, and Exit options using `CardLayout`. |
| `GamePanel` | Central game panel. Extends `JPanel` and implements `Runnable`. Contains the game loop (60 FPS target), manages level transitions, spawning, painting, and references to all game objects. |
| `KeyHandler` | Implements `KeyListener`. Tracks all key states as boolean flags (movement, attack, pick up, drink, drop, navigate, menu, dialogue). |
| `Inventory` | Renders the 10-slot hotbar HUD, handles item selection, potion usage, and item dropping. |
| `LiveHealthBar` | Draws the health bar, karma bar, and coin count in the top-left corner of the screen. |
| `ScreenManager` | Draws the Game Over screen (with RELOAD/EXIT buttons), Level Complete screen, and final Game End screen. |
| `inGameMenu/MenuIngame` | Draws the in-game pause menu (Resume / Save / Exit) and the save slot selection screen. Handles saving to any of 7 SQLite slots via `DataBaseSaveSlotManager`. |

---

### `Entity`

All characters and related factory/interface hierarchy.

#### Interfaces & Abstract Classes

| Type | Name | Description |
|---|---|---|
| Interface | `Characterr` | Base interface for all characters. Requires `getPlayerImage()`, `setPlayerSpawn()`, `draw()`. |
| Interface | `MainCharacter` | Extends `Characterr`. Adds `update()`, `getHurt()`, and Observer pattern methods. |
| Interface | `AggroCharacter` | Extends `Characterr`. For hostile enemies — adds `update(harta, player)`. |
| Interface | `NPC` | Extends `Characterr`. For non-hostile NPCs — adds `speak()`, `update()`, `chooseResponse()`, dialogue index getters/setters. |
| Interface | `PlayerObserver` | Observer pattern. `updateObs(player)` is called when player state changes. |
| Abstract | `Entity` | Base class for Player, Orco. Holds sprite frames, position (`x`,`y`), speed, life, attack, collision box. |
| Abstract | `CharacterFactory<T>` | Generic Abstract Factory base. |

#### Concrete Characters

| Class | Type | Description |
|---|---|---|
| `Player` | `MainCharacter` / `Entity` | The player character. Handles movement, collision, spritesheet animation (walk, attack, hurt), inventory management, karma/life tracking, dialogue flags, and level progression flags. |
| `Orco` | `AggroCharacter` / `Entity` | Orc enemy in Level 1 (Inferno). Chases the player within aggro range, attacks on contact, handles collision avoidance via `CollisionCheck`. |
| `Ghost` | `AggroCharacter` | Ghost enemy in Level 2 (Purgatory). Moves randomly when the player is within 240px, deals contact damage. No collision with map (passes through walls). |
| `Mike` | `NPC` | NPC in Inferno. 5-dialogue moral conversation about betrayal and regret. |
| `Son` | `NPC` | NPC in Purgatory. 5-dialogue conversation about fatherhood and truth. Giving a `Skull` item affects karma. |
| `Sot` | `NPC` | NPC in Paradise. Part of a two-NPC couple dialogue sequence (alternates with `Sotie`). |
| `Sotie` | `NPC` | NPC in Paradise. Counterpart to `Sot`, shares a `priorityflag` for alternating dialogue turns. |
| `Seller` | `NPC` | Shop NPC. Allows purchasing potions and items with coins. |
| `OracolInfern` | `NPC` | Oracle NPC in Inferno. Provides exposition and level guidance. |
| `OracolParadis` | `NPC` | Oracle NPC in Paradise. Provides final narrative context. |

#### Factories

| Class | Creates |
|---|---|
| `MainCharacterFactory` | `Player` (type: `"player1"`) |
| `NPCFactory` | `Sot`, `Sotie`, `Seller`, `OracolInfern`, `OracolParadis`, `Mike`, `Son` |
| `AggroCharacterFactory` | `Orco` (type: `"orc"`), `Ghost` (type: `"ghost"`) |

---

### `Harta` (Map)

Handles tile-based map loading and rendering from CSV layer files.

| Class | Description |
|---|---|
| `InterfaceHarta` | Interface defining `getTileFromTileset()`, `isCollidable()`, `getMapImage()`, `getCollisionlayer()`, `loadCSVMap()`. |
| `HartaInfern` | Level 1 map. Loads 10 CSV layers from `resources/layer/inferno/`. Tileset: `tileinferno.png` (3072px wide). Collision tile ID: `505`. |
| `HartaPurgatoriu` | Level 2 map. Loads 5 CSV layers from `resources/layer/purgatorio/`. Tileset: `Tileset.png`. Collision tile ID: `205`. |
| `HartaParadis` | Level 3 map. Loads 15 CSV layers from `resources/layer/paradis/`. Tileset: `tileparadiiiiiiiiiis.png` (576px wide). Collision tile ID: `175`. |

Each map:
- Uses a `HashMap<Integer, BufferedImage>` tile cache to avoid redundant `getSubimage()` calls.
- Implements frustum culling — only tiles within the current camera viewport are drawn.
- Has a dedicated `draw(...)` method that also renders entities at the correct layer depth.

---

### `Obiecte` (Objects)

All pickable items and their management.

#### Interfaces

| Interface | Description |
|---|---|
| `ObjInterface` | Base for all pickable objects. Requires `getImg()`, `draw()`, `getX/Y()`, `setX/Y()`, `getIsPicked()`, `setIsPicked()`, `getDefaultImg()`, `getID()`. |
| `PotionInterface` | Extends `ObjInterface`. Adds `getAffectByPotion(Player)` for use-effects. |
| `MoneyInterface` | Interface for `Money`. Requires `getImg()` and `draw()`. |

#### Items

| Class | Type | Effect |
|---|---|---|
| `HealthPotion` | `PotionInterface` | Restores 34 HP (capped at 200). Animated 6-frame sprite. |
| `SpeedPotion` | `PotionInterface` | Increases player speed by 26% (capped at 3.0). Animated 6-frame sprite. |
| `InutilPotion` | `PotionInterface` | Deals 20 damage and grants +1 karma. Animated 6-frame sprite. |
| `KeyOne` | `ObjInterface` | Key for Level 1. Singleton. 24-frame animated sprite. Cannot be dropped. |
| `KeyTwo` | `ObjInterface` | Key for Level 2. Singleton. 12-frame animated sprite. Cannot be dropped. |
| `KeyThree` | `ObjInterface` | Key for Level 3. Singleton. 12-frame animated sprite. Cannot be dropped. |
| `Ring` | `ObjInterface` | Quest item used in Sot/Sotie dialogue. Single-frame sprite. |
| `Skull` | `ObjInterface` | Quest item used in Son dialogue. Giving it causes a large karma penalty. |
| `Money` | `MoneyInterface` | Coin collectible. 7-frame animated sprite. Grants +13 coins on pickup. |

#### `MoneyManager`

Manages coin spawning, collection, and rendering. Spawns 300 coins in Inferno and 20 in other maps, placed in non-collidable tiles. Uses a copy-on-iterate list to avoid `ConcurrentModificationException`.

---

### `Camera`

| Class | Description |
|---|---|
| `Camera` | Tracks world-space viewport position (`x`, `y`) and `scale` (default: `4.0`). `update(player)` centers the camera on the player. `apply(Graphics2D)` applies an `AffineTransform` (translate + scale). Clamps position to map bounds to prevent showing areas outside the map. |

---

### `Collision`

| Class | Description |
|---|---|
| `CollisionCheck` | `checkCollision(entity, harta)` — returns `true` if the entity's collision box will enter a solid tile in the current movement direction. `avoidCollision(harta, entity)` — redirects enemies around walls by testing adjacent directions when a collision is detected. Used by `Orco` and `Player`. |

---

### `DatabaseManager`

| Class | Description |
|---|---|
| `SaveData` | Plain data container (DTO). Holds all fields needed to restore a game state: level, life, coins, karma, position, NPC dialogue indices, enemy list, inventory, field items, and coins on field. |
| `DataBaseSaveSlotManager` | Manages SQLite persistence via JDBC (`org.sqlite.JDBC`). Handles: saving/loading player state, inventory (serialized object mapping), field items, enemy states, coin positions, and individual NPC dialogue indices. Supports 7 named save slots. Also writes winners to a `WINNERS` table for the leaderboard. |

---

## Design Patterns Used

| Pattern | Where |
|---|---|
| **Abstract Factory** | `CharacterFactory<T>` → `MainCharacterFactory`, `NPCFactory`, `AggroCharacterFactory` |
| **Singleton** | `KeyOne.getInstance()`, `KeyTwo.getInstance()`, `KeyThree.getInstance()` — ensures one key instance per level |
| **Observer** | `PlayerObserver` interface + `Player.addObserver()` / `notifyObservers()` / `checkState()` — reacts to player state changes (e.g., death) |
| **Strategy (implicit)** | `PotionInterface.getAffectByPotion()` — each potion type implements its own effect |
| **Template Method (implicit)** | `Entity` abstract class provides common fields; subclasses implement `getHurt()` and draw logic |
| **Data Transfer Object** | `SaveData` — carries save state between database and game |

---

## Game Mechanics

### Levels

| Level | Map | Enemies | Key NPCs |
|---|---|---|---|
| 1 — Inferno | `HartaInfern` | Orcs (`Orco`) | Mike, OracolInfern, Seller |
| 2 — Purgatory | `HartaPurgatoriu` | Ghosts (`Ghost`) | Son, Seller |
| 3 — Paradise | `HartaParadis` | None | Sot, Sotie, OracolParadis |

### Karma System

- Karma ranges roughly from -500 to +500.
- Good dialogue choices award karma; bad choices deduct it.
- Final ending: positive karma → "YOU WIN!", negative karma → "GAME OVER" screen.
- Karma is displayed as a bidirectional bar centered at zero in the HUD.

### Health System

- Player starts with 200 HP.
- Orcs and Ghosts deal damage on contact.
- Health Potion restores 34 HP (max 200).
- Inutile Potion deals 20 damage.
- On death, a Game Over screen with RELOAD/EXIT options is shown.

### Inventory

- 10 slots, displayed as a hotbar at the top of the screen.
- Navigate with `K` (left) and `L` (right).
- Drink potion with `I`.
- Drop item with `O` (keys cannot be dropped).
- Selected slot shows item name in a tooltip below the hotbar.

---

## Controls

| Key | Action |
|---|---|
| `W A S D` | Move up / left / down / right |
| `P` | Pick up item |
| `K / L` | Navigate inventory left / right |
| `I` | Use (drink) selected item |
| `O` | Drop selected item |
| `R` | Speak to nearby NPC |
| `Space` | Advance dialogue |
| `←→` (during dialogue) | Select dialogue response |
| `Enter` | Confirm selection / attack |
| `M` | Open/close in-game pause menu |
| `Escape` | Close dialogue |

---

## Save System

- 7 save slots, accessible from the in-game pause menu (press `M` → SAVE).
- Save stores: level, HP, karma, coins, player position, all NPC dialogue indices, inventory contents, field items, enemy states, and coin positions.
- Load is accessible from the main menu's LOAD GAME screen.
- Saving uses a `ReentrantLock` to prevent concurrent write issues.

---

## Database

SQLite database located at `resources/database/savedStates.db`.

Tables managed by `DataBaseSaveSlotManager`:

| Table | Contents |
|---|---|
| Save slot table(s) | Player stats, position, level per slot |
| Inventory table | Serialized item type + quantity per slot |
| Field items table | Items dropped on the map per slot |
| Coins table | Coin positions per slot |
| Enemies table | Orc positions and state per slot |
| Dialogue index table | Per-NPC dialogue progress per slot |
| `WINNERS` | Name + karma for leaderboard (written on game completion) |

---

## Resource Structure

```
resources/
├── tileset/
│   ├── tileinferno.png        # Inferno tileset (3072px wide)
│   ├── Tileset.png            # Purgatory tileset
│   └── tileparadiiiiiiiiiis.png  # Paradise tileset (576px wide)
├── layer/
│   ├── inferno/               # 10 CSV layer files for Level 1
│   ├── purgatorio/            # 5 CSV layer files for Level 2
│   └── paradis/               # 15 CSV layer files for Level 3
├── Sprites/
│   ├── player spritesheets    # Walk, attack, hurt animations
│   ├── gosth.png              # Ghost enemy spritesheet
│   ├── mikee.png, fiuu.png, sottt.png, ...  # NPC spritesheets
│   ├── SmallHealthPotion.png, SmallStaminaPotion.png, BigManaPotion.png
│   ├── key_32x32_24f.png, key-blue.png, key-white.png
│   ├── Ring.png, skull2.png
│   └── PileOfGold.png
└── database/
    └── savedStates.db         # SQLite database (auto-created on first save)
```

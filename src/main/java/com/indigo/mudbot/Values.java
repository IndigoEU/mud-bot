package com.indigo.mudbot;

import com.indigo.mudbot.Classes.ClassTemplate;
import com.indigo.mudbot.Classes.Fighter;
import com.indigo.mudbot.Commands.CreateCharacter;
import com.indigo.mudbot.Commands.JoinGame;
import com.indigo.mudbot.Commands.Ping;
import com.indigo.mudbot.Commands.Say;
import com.indigo.mudbot.Items.ItemTemplate;
import com.indigo.mudbot.Items.Bone;
import com.indigo.mudbot.Items.HealingPotion;
import com.indigo.mudbot.Items.Slimeball;
import com.indigo.mudbot.Weapons.WeaponTemplate;
import com.indigo.mudbot.Weapons.Battleaxe;
import com.indigo.mudbot.Weapons.Bow;
import com.indigo.mudbot.Weapons.Dagger;
import com.indigo.mudbot.Weapons.Flail;
import com.indigo.mudbot.Weapons.Hammer;
import com.indigo.mudbot.Weapons.Longsword;
import com.indigo.mudbot.Weapons.Rapier;
import com.indigo.mudbot.Weapons.Scythe;
import com.indigo.mudbot.Weapons.ShortSword;
import com.indigo.mudbot.Weapons.Shovel;
import com.indigo.mudbot.Weapons.Spear;
import com.indigo.mudbot.Weapons.Torch;
import com.indigo.mudbot.Monsters.MonsterTemplate;
import com.indigo.mudbot.Monsters.DemonCat;
import com.indigo.mudbot.Monsters.GnomeReaper;
import com.indigo.mudbot.Monsters.MutatedFlesh;
import com.indigo.mudbot.Monsters.SaltElemental;
import com.indigo.mudbot.Monsters.ScrissorRat;
import com.indigo.mudbot.Monsters.Skeleton;
import com.indigo.mudbot.Monsters.Slime;
import com.indigo.mudbot.Monsters.SmallFungus;
import com.indigo.mudbot.Monsters.Spider;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

public class Values {
    public static Command[] Commands = new Command[]{
            new Ping(),
            new CreateCharacter(Main.waiter),
            new JoinGame(Main.waiter),
            new Say()
    };
    public static ClassTemplate[] playerClasses = new ClassTemplate[]{
            new Fighter()
    };
    public static ItemTemplate Map<String, Item> = new ItemTemplate Map<String, Item> {
            new Bone();
            new HealingPotion();
            new Slimeball();
    };
    public static WeaponTemplate Map<String, Item> = new WeaponTemplate Map<String, Item> {
            new Battleaxe();
            new Bow();
            new Dagger();
            new Flail();
            new Hammer();
            new Longsword();
            new Rapier();
            new Scythe();
            new ShortSword();
            new Shovel();
            new Spear();
            new Torch();
    };
    public static MonsterTemplate Map<String, Monster> = new MonsterTemplate Map<String, Monster> {
            new DemonCat();
            new GnomeReaper();
            new MutatedFlesh();
            new SaltElemental();
            new ScrissorRat();
            new Skeleton();
            new Slime();
            new SmallFungus();
            new Spider();
    };
}

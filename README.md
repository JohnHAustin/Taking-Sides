Taking-Sides
============

Contents

  1) Forward
  
  2) Type File
  
  3) Beta To-Do List
  
  4) V1.0 To-Do List

============

1) Forward

Taking Sides was initially created by JamioFlan and JJBrazman for Ludum
Dare 26. Written and released over the course of a single weekend, it comprised
entirely of ideas, code and music from the imaginations of its creators
(although it borrows heavily from the library LWJGL). Following the
competition, it was briefly hosted on JamioFlan's website, and then left on the
back-burner for a year and a half. Now resurrected as an ongoing project, it
aims to be a fun, free-to-play game on the internet as a source of enjoyment,
an opportunity for experimentation and a showcase of talent. Submissions and
ideas are welcome.


2) Type File

The ideal notepad++ type file for this readme (TSType.xml) is in the main
project folder.


3) Beta To-Do List 7 DONE 5 INPR 8 TODO

This list is not to be added to; when all items are completed we will have
reached Beta.

i) Pause button

I want the game to pause, rather than quit, when you press ESC, and to say
'PAUSED'.

It now pauses, but there's no message.
INPR

ii) Fire goes out

In the alpha, fire can never be stopped.

Each fire attack increases the time until it goes out by 30 ticks.
DONE

iii) Creatures are finite

Creatures respawn every time you kill them.

Slimes, fire slimes and snakes now respawn every other time.
DONE

iv) Upgrades are finite

You can farm upgrades early to make later levels easier.

Changed so you can only upgrade any weapon to the level above the level you're
on.
DONE

v) Creature size = health

To make the game more dynamic, creature size should represent their current
health.

Adjusted slimes to show their size, limited to between 10 and 160. Still need
to adjust snakes and check fire slimes
INPR

vi) Slime shot hurts them

Slimes should hurt themselves with each shot.

Done, they also slow down when on low life.
DONE

vii) Slime shot is standard size and colour

Slime shot needs to be more easily identifiable.

It's now a standard purple, as is the SnowflakeBoss, and has a minimum size.
DONE

viii) Upgrade speed each level

Being slow is boring on later levels, and makes you feel week.

Speed is now governed by two variables, and your base speed is equal to the
level number.
DONE

ix) Blood = health

To make the game more dynamic, make blood into a health pickup and remove the
health drop.

Blood is now equal to health, but may need tweaking. Health drop is still in to
make testing easier. Blood is hard to pick up; should probably increase the
player hitcircle, but with care to avoid making the game too hard.
INPR

x) Temp speed boost from kills

As part of the drive to make things more dynamic, scrap speed boost and give a
small temporary boost each kill.
TODO

xi) Slimes don't hurt the Snowflake Boss

Slimes and the boss shouldn't hurt one another.

They no longer collide with one another.
DONE

xii) Shotgun

New weapon: it fires a mass of shots ahead. Possibly base number on level of
weapon, or standardise but increase power. Maybe replace the blade, and give
the player the blade after the SnowflakeBoss?
TODO

xiii) Decrease fire boss attack speed

The fire boss attacks way too fast, and produces too many fire slimes.

Boss attack is slowed down, still need to look into the slimes.
INPR

xiv) Snake max length = 6 + level

The snakes are too long, should be limited in length to the suggested.
TODO

xv) Average creature size doesn't change per level

Slimes, snakes and fire slimes need to gain life each level but remain a
similar size.

Reworked for slimes, need to check the other too.
INPR

xvi) Fire attack stops and scales

The fire weapon goes on until its hits something, which is bad for the computer
and the game. Need to limit its distance. and make it recharge more quickly
for higher levels.
TODO

xvii) Render upgrades last

Upgrades can get lost beneath trees. Should render upgrades last. Also, maybe
upgrades should disappear (and decrement the relevent variable) so that they
can be replaced if you miss them.
TODO

xviii) New creature: Superslime (level 2)

I want to push back the fire slimes and boss to level 3, and put a new creature
in. This one would, upon dying, break into x slimes (where x is the number of
its sides). When in superslime form, should track you (unlike the normal
slimes) and maybe have a different attack. Limited numbers per level.
TODO

xix) New Boss: SuperDuperTrooperSlime (level 2)

The boss of the superslimes splits into 4 creatures, each of which splits into
4 superslimes. Should be more interesting than a normal superslime. Drops the
Scatter Gun.
TODO

xx) Bosses dispay health.

Rather than getting smaller, maybe bosses could turn black to indicate life?
TODO


4) V1.0 To-Do List

Add to this list as much as you want; when the beta list is finished I'll comb
it and see what should be pushed and what should be prioritised, but for now
it's a repository for things to do that won't make the beta.

i) Score

Some kind of score would be nice. The score available from each level should be
double the previous to avoid it being worth farming.

ii) Status Screen

Upon pause, it would be nice to have a status screen, maybe with buttons and
the score.

iii) Turn sound off

A button to turn off the sound would be nice. Probably put it on the status
screen above.

iv) Twist weapons

The square shot (for example) is a different orientation when fired. Ideally
this wouldn't be the case

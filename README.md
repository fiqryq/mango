## Mango
![Android CI](https://github.com/fiqryq/Mango-Master/workflows/Android%20CI/badge.svg)
![Build Status](https://dev.azure.com/fiqrychoerudin/Mango/_apis/build/status/fiqryq.Mango-Master?branchName=master)

Mango Is Attendance App using [Estimote Proximity Beacon](https://estimote.com/products/).

## Prototype
- Full Ui Manog [here](https://bit.ly/UIMango) 
- interactive UI Dosen [here](https://bit.ly/PrototypeMangoDosen)
- interactive UI Mahasiswa [here](https://bit.ly/ProtoMHS)

## Document
- Journal
- Video [here](https://bit.ly/PromosiMango)
- Poster [here](https://bit.ly/PosterMango)

## Add Proximity SDK
```gradle
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    // ...

    // add this:
    implementation 'com.estimote:proximity-sdk:1.0.4'
    // for the latest version, see the CHANGELOG and replace "1.0.4"
    // https://github.com/Estimote/Android-Proximity-SDK/blob/master/CHANGELOG.md

    implementation 'com.estimote:mustard:0.2.1'

    // for compatibility with Android 10, also add:
    implementation 'com.estimote:scanning-plugin:0.25.4'
}
```
## Set up Estimote Cloud credentials
```java    
        // add this:
        EstimoteCloudCredentials cloudCredentials =
            new EstimoteCloudCredentials("APP ID", "APP TOKEN");
```

## Create a Proximity Observer object
```java  
    // 1. Add a property to hold the Proximity Observer
        private ProximityObserver proximityObserver;

    // 2. Create the Proximity Observer
            this.proximityObserver =
                new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                    .onError(new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {
                            Log.e("app", "proximity observer error: " + throwable);
                            return null;
                        }
                    })
                    .withBalancedPowerMode()
                    .build();
```
# Define Proximity Zones
Time to tell our Proximity Observer what enter/exit events we’re interested in! But first, some theory:

## Tags and attachments
Chance is, knowing when the user enters range of beacon 1b4fe is not that useful in and on itself. However, if your app somehow knows that beacon 1b4fe is placed on Peter’s desk … “In range of beacon 1b4fe” suddenly means “close to Peter’s desk”.
In other words, most of the time, proximity to beacons only makes sense if you can give your beacons some meaning. That’s what beacon tags and attachments are for … an easy way for you to attach some extra data/meaning to a beacon, for example:

```
    "identifier": "1b4fe",
    "tag": "desks",
    "attachments": {
       "desk-owner": "Peter"
     }
```

With such a setup, it’s now easy to say “monitor proximity to desks”, and also figure out whose desk we’re close to.
And since tags and attachments are stored in Estimote Cloud, it’s also easy to change the setup without having to change the app’s code. 
For example, if sombebody else takes Peter’s desk, just change the “desk-owner” to a new one. If your app was coded to welcome the owner at their desk by their name, it’ll now start using the new one.

## Set up tags and attachments
With the theory out of the way, let’s set up some tags and attachments.
Go to cloud.estimote.com, select one of your beacons, and click “Edit”. Then:
- to set up a tag: Click on “Tags”, “Create New Tag”, enter “desks”, and click “Add”.
- to set up attachments: On the left side, select “Beacon Attachment”. Then, add a “desk-owner” key with value set to “Peter”.

Finish with the “Save Changes” at the bottom. Ta-da, we’ve just set up our first beacon!
Repeat these steps for another beacon. Use the same “desks” tag, but this time, set the “desk-owner” to “Alex”.

## Create Proximity Zone objects
Now we can move on to creating Proximity Zone objects in our app. Back to the future MainActivity!
```java  
    this.proximityObserver = // ...
    
    // add this below:
    ProximityZone zone = new ProximityZoneBuilder()
        .forTag("desks")
        .inNearRange()
        .onEnter(new Function1<ProximityZoneContext, Unit>() {
            @Override
            public Unit invoke(ProximityZoneContext context) {
                String deskOwner = context.getAttachments().get("desk-owner");
                Log.d("app", "Welcome to " + deskOwner + "'s desk");
                return null;
            }
        })
        .onExit(new Function1<ProximityZoneContext, Unit>() {
            @Override
            public Unit invoke(ProximityZoneContext context) {
                Log.d("app", "Bye bye, come again!");
                return null;
            }
        })
        .build();
```
Note that for this setup to work, you need to spread the beacons apart a good few meters. If they overlap, then moving from one beacon to the other is considered moving within the zone, and it won’t trigger additional enter/exit actions.
If you want to know about movements inside a zone spanned by multiple overlapping beacons, you can use the “onContextChange” action instead. Think about it as: I’m still in the same desks zone (hence no new enter/exits), but my context (which specific desks are in range) has changed (hence the “onContextChange” action).

```java 
    ProximityZone zone = new ProximityZoneBuilder()
        // ...
        .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
            @Override
            public Unit invoke(Set<? extends ProximityZoneContext> contexts) {
                List<String> deskOwners = new ArrayList<>();
                for (ProximityZoneContext context : contexts) {
                    deskOwners.add(context.getAttachments().get("desk-owner"));
                }
                Log.d("app", "In range of desks: " + deskOwners);
                return null;
            }
        })
```
Here’s how this would work in an overlapping scenario:

```
    # move in range of "Peter's desk" beacon
    # this is also when the "enter" action would get called
    Nearby desks: [Peter]
    # move in range of both beacons
    Nearby desks: [Peter, Alex]
    # move out of range of "Peter's desk", but still in range of "Alex's desk"
    Nearby desks: [Alex]
    # move out of range of both beacons
    # this is also when the "exit" action would get called
    Nearby desks: []
```
## About the Proximity Zone range

```java 
    ProximityZone innerZone = new ProximityZoneBuilder()
        .forTag("treasure")
        .inCustomRange(3.0)
        .create();
    
    ProximityZone outerZone = new ProximityZoneBuilder()
        .forTag("treasure")
        .inCustomRange(9.0)
        .create();
```

## Start proximity observation
We’re almost there! Before we start proximity observations, there’s just one more thing left to do.

## Request location permissions
Performing a Bluetooth scan on Android requires the app to obtain an ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permission from the user. There’s a tutorial on Android Developer Portal which shows how to do that: Requesting Permissions at Run Time. However, to get started quickly, we’ll use our little helper library, com.estimote:mustard, to check and request the appropriate permissions with just a few lines of code.
As an added bonus, it’ll also check if all the other requirements are met—for example, if Bluetooth is available and turned on.

```java 
    RequirementsWizardFactory
        .createEstimoteRequirementsWizard()
        .fulfillRequirements(this,
        // onRequirementsFulfilled
        new Function0<Unit>() {
            @Override public Unit invoke() {
                Log.d("app", "requirements fulfilled");
                proximityObserver.startObserving(zone);
                return null;
            }
        },
        // onRequirementsMissing
        new Function1<List<? extends Requirement>, Unit>() {
            @Override public Unit invoke(List<? extends Requirement> requirements) {
                Log.e("app", "requirements missing: " + requirements);
                return null;
            }
        },
        // onError
        new Function1<Throwable, Unit>() {
            @Override public Unit invoke(Throwable throwable) {
                Log.e("app", "requirements error: " + throwable);
                return null;
            }
        });
```

## Tech 👨‍💻

- [Picasso](https://square.github.io/picasso/) - Picasso
- [hdodenhof](https://github.com/hdodenhof/CircleImageView) - hdodenhof
- [KAlertDialog](https://github.com/TutorialsAndroid/KAlertDialog) - KAlertDialog
- [Firebase](https://firebase.google.com/docs/android/setup?authuser=0) - Firebase
- [Estimote SDK](https://developer.estimote.com/) - Estimote SDK

## Task List
- [x] Multi Role Login

## LEGAL
Copyright 2019-2020 Autumn Leaves Program Studi D3 Rekayasa Perangkat Lunak Aplikasi Universitas Telkom Bandung.
**Don't upload** any fork ,source on any other paltform.

## Who Supported This Project (All lecturers of D3IF Telkom University)
- Rahmadi Wijaya, S.Si., M.T. (Head of Study Program)
- Gandeva Bayu Satrya, ST., MT., Ph.D. (Advisor 1)
- Hariandi Maulid , S.T., M.Sc
- Amir Hasanudin Fauzi, S.T., M.T.
- Fat'hah Noor Prawita, S.T., M.T.
- Hariandi Maulid, S.T., M.Sc.
- Hetti Hidayati, S.Kom., M.T.
- Indra Azimi, S.T., M.T.
- Rizza Indah Mega Mandasari, S.Kom., M.T.
- Tri Brotoharsono, S.T., M.T.

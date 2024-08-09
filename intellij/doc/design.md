## Domain Model

```plantuml
@startuml
hide circle
hide empty methods
' classes
class StorePost{
date
caption
type
length
difficulty
}
class List{
}

class Cardio{
   EnduranceFocus
   AgilityFocus
   SpeedFocus
}

class Strength{
   UpperBodyFocus
   LowerBodyFocus
   FullBodyFocus
   BodyWeightFocus
}

class SearchFilter{
date
type
length
difficulty
}

' associations
StorePost "1" - "1" Workout : \tShares\t\t
Workout "1" - "1" Cardio : \tDescribes\t\t
Workout "1" - "1" Strength : \tDescribes\t\t
List "1" - "1..*" StorePost : \tShows\t\t
SearchFilter "1" - "1" List : \tCreates\t\t
@enduml
```

# Sequence Diagrams

### Creating a profile:
```plantuml
@startuml
hide footbox
actor User as user
participant ": View" as view 
participant ": Controller" as controller 
participant ": Profile" as profile

user -> view : CreateProfile
view -> controller : onCreateButton()
controller ->  profile : Profile()
controller -> profile : onAddedPassword
controller -> profile : onAddedUsername
controller -> profile : onAddedBio
Profile -> view : Display Feed (Profile Created)
@enduml
```

### Posting a workout:
```plantuml
@startuml
hide footbox
actor Producer as producer
participant ": View" as view 
participant ": Controller" as controller 
participant ": Post" as Post
participant ": Workout" as workout

producer -> view : AddPost 
view -> controller : onAddPost()
controller -> Post : Post(caption, type, length, difficulty)
activate Post
deactivate Post
workout <- Post : onWorkoutButton()
activate workout
deactivate workout
workout -> Post : onAddedWorkout()
view <- Post : toString()
activate Post
deactivate Post
@enduml
```

### Seeing the Feed
```plantuml 
@startuml
hide footbox
actor "User" as user 
participant "Post" as post
participant "feed : Feed" as feed
participant "View" as view


user -> view : Feed
view <- feed : toString()
feed <- post : toString()


@enduml
```

### Filtering content
```plantuml 
@startuml
hide footbox
actor Lurker as lurker
participant "view" as view
participant "Controller" as controller 
participant "Filter" as filter
participant "Length" as length
participant "Type" as type 
participant "Sport" as sport
participant "Difficulty" as difficulty
participant "feed : Feed" as feed 


lurker -> view : Filter 
view -> controller: onFilter() 
controller -> length: Length(length, feed)
controller -> difficulty: Difficulty(difficulty, feed)
controller -> type : Type(type, feed)
controller -> sport : Sport(sport, feed)
controller <- length: filter()
controller <- difficulty: filter()
controller -> feed : filteredFeed(length, difficulty, type, sport)
feed -> view : toString()
@enduml
```

### Following a Profile:
```plantuml
@startuml
hide footbox
actor Producer as producer
participant ": View" as view 
participant ": Controller" as controller 
participant ": Feed" as feed
participant ": Profile" as profile
 
view -> controller : onProfileClick()
controller -> feed : ViewProfileView(Post.Profile)
view <- profile : OtherProfileView
producer -> profile : requestFollow(profile, otherProfileView)
@enduml
```

### Accepting a Follow Request:
```plantuml
@startuml
hide footbox
actor Producer as producer
participant ": View" as view 
participant ": Controller" as controller 
participant ": Feed" as feed
participant ": Profile" as profile
participant ": FollowRequest" as requests
 
view -> controller : viewProfile()
controller -> feed : ViewProfileView(producer.profile)
producer -> profile : onFollowRequests()
controller -> view : FollowRequestView()
producer -> requests : onAccept(profile)
@enduml
```

## Class Diagram 

```plantuml
@startuml
'skinparam classAttributeIconSize 0
class Post {
-String producer_ID
-String Caption
-{Static} int WRK_Limit
{method} +addWorkout(type) 
{method} +addCaption()
{method} +toString() : String
}

abstract class Workout {
#int length 
#int difficulty 
#String Description
{method} {abstract} +createWorkout()
}

class CardioWorkout {
-Boolean EnduranceFocus
-Boolean AgilityFocus 
-Boolean SpeedFocus
--
createWorkout() 
}

class StrengthWorkout {
-Boolean UpperbodyFocus
-Boolean LowerbodyFocus 
-Boolean FullbodyFocus
-Boolean BodyweightFocus 
--
createWorkout() 
}

class MobilityWorkout{
-Boolean dynamicStreching
-Boolean staticStreching
-Boolean yoga
--
createWorkout()
}
class Feed {
ArrayList<Post> feed
--
-Filter(length: int, difficulty : int, type: ) : Stack<Post> 
+toString() : String
}

class Controller {
--
}

class View {
}

class Profile {
String username
String password
Feed posts
String bio
--
}

interface Filter {
filter()
}

class Length {
int length
Array<post> feed
--
filter()
}

class Difficulty {
int difficulty
Array<post> feed
--
filter()
}

class Type {
int type
Array<post> feed
--
filter()
}

class Sport {
String sport 
Array<post> feed 
--
filter ()
}

Feed *-"(1..*) Posts \n{ordered, Stack}\n Can be filtered or not" Post : \t\t\t\t\t\t\t
Post -> "1 (workout)" Workout : \t\t\t
Workout <|-- CardioWorkout
Workout <|-- StrengthWorkout
Workout <|-- MobilityWorkout
Controller -- Post 
Controller -- Feed
Controller -- Profile
Profile -- Feed
View -- Controller
Filter <|-- Length
Filter <|-- Difficulty
Filter -- Feed
Filter <|-- Type
Filter <|-- Sport
Controller -- Filter
@enduml
```

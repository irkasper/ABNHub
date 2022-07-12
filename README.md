# ABN Take-home Challenge

Hi there! Thanks for reviewing my submission. I had fun writing it and I hope it's at least mildly
interesting to review. I've included some high-level thoughts on my approach below. Obviously feel
free to send any questions my way (hadiamouhashemi@gmail.com).

## Architecture

I used the MVVM architecture

You'll note that I subdivided Google's 'UI Layer' into two parts: a UI fraction (dumbly binds data
emitted by the view model and reports user events back to the view model), and the view model
itself. Pretty standard stuff.

Data flows uni-directionally, and each layer adheres to strict limitations on the dependencies it's
allowed. As usual, a layer can only depend on entities from the layer immediately beneath it (e.g.,
the UI layer can depend on the view model layer but not on the data layer). In the interest of
testability, only the UI layer can depend on the Android framework.

Speaking of testability, I’ve included DI containers and injected dependencies throughout. If the
app grew substantially, a DI framework like Hilt (or Dagger outright) or Koin would be in order.

`Fragment`s are my modular UI element and the app consists of a single `Activity` (and would be
expected to stay that way as it grew). Interaction with `FragmentManager` is left to the internals
of the Navigation component. (See below for my discussion as to why I bothered introducing the Nav
component into such a tiny app.)


I've used coroutines as my asynchronous programming model and Flow for reactive streams.


# UI cheats
    
For demonstration purpose only, I wrote the `RepoListFragment` using XML files and for the `RepoDetailsFragment` I used Jetpack Compose
, otherwise I had to choose one of the XML or Compose ways!

# No styles

I sadly didn't have time to design a well-styled app, I spend most of my time on the code side.


# Tests

Sadly I only didn't have time to write all tests. But perhaps that's expected (?). 
Regardless, `RepoRepositoryImplTest`, `End2EndTest` and `RepoListFragmentTest` should give you a sense of what my unit tests look like.


# No git history

I rebased and squashed all my messy git history. I was hoping to have time to fake something that
tracked the requirements from the project prompt, but no luck.

For the rest, see my comments throughout. They should clue you into any knowing short-cuts taken in
interest of keeping a toy project (at least somewhat) restrained.


Thanks again for getting this far – looking forward to the conversation!

# Ex13_NavigationBar
Lecture 02 - Development of Graphical User Interfaces (GUI)

The user can navigate through four different Fragments by means of a NavigationBar. 
- Navigation is automatically handled by the NavController by using the same Id for the Fragments in the Navigation graph and the MenuItems in the Menu associated to the NavigationBar.
- Action elements in the ActionBar:
  - Activate a simple Badge for the first Fragment.
  - Increase the current number of a numeric Badge for the third Fragment.
- OnDestinationChangedListener() is also used to react when the user navigates to these Fragments to deactivate the associated Badges.

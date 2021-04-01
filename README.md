# Frog Study Tracker

## Project Proposal
As my personal project this term, I would like to design a task tracker 
that would record a student's task after they have completed it. 
In order to make studying more appealing, a student can
help feed a frog every time they have completed a task. The frog will level up over time.
In addition, they can also see an overview of how much they have accomplished sorted by subject, 
task type, and date.

This application would be designed for students who may need a bit of extra 
motivation to get to work. As a student myself, I understand how stressful and 
overwhelming it can get during the school year. Furthermore, I believe that the 
pressure that comes with school can be a significant contributing factor to student procrastination.
Therefore, I hope that by *gamifying* studying and schoolwork, students can look 
forward to completing some of the more mundane or unappealing tasks.

## User Stories
- As a user, I want to be able to add a task
to my tracker.
- As a user, I want to be able to remove
a task from my tracker.
- As a user, I want to be able to edit a
recorded task on my tracker.
- As a user, I want to be able to view all the
tasks I have completed.
- As a user, I want to be able to view the tasks
completed for a given subject or type.
- As a user, I want to be able to save my task list to file.
- As a user, I want to be able to load my task list from file.
- As a user, when I select the quit option from the menu, I want to 
be reminded to save my task list to file and have th eoption to do so or not.
- As a user, when I start the application, I want to be given the option to load my task list from file.


## Phase 4: Task 2
- I chose to test and design a class in the model package that is robust.
- The class I chose to make robust was TaskList
- The methods that are now robust include: removeTask, editTaskName,
displayTaskOfType, displayTaskOfSubject, editTaskName, editTaskSub
editTaskType, editTaskDur, editTaskDescription

## Phase 4: Task 3
- I would want to refactor my project so that TaskListGUI does not know about the Listener classes
by just initiating a new class inside of the addListener methods
I find that bidirectional association unnecessary
- I also feel like there is too much coupling between the Edit Listeners, I would probably want to add
an abstract class at a higher level and make the listeners extend
that class so that I wouldn't have to make changes in every class when I
just want to make one change. It is also possible to merge them into one class, and
have the function differ according to which text field has text in it. However,
that may make the class incoherent.
- Similarly, there is also some coupling between FilterByTypeListener and FilterBySubjectListener.
I think there should be a higher level class which they extend to have a
a single point of control.
- AddListener also doesn't need to be associated with TaskList or Task. It should be able to
complete all its functions just by being associated with TaskListGUI
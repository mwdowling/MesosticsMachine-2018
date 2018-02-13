# MesosticsMachine-2018

The .java files in this repository comprise a MesosticsMachine, a software system to aid in the implementation of a score by John Cage to create a musical realization of a literary work.  This is a nearly complete, fully refactored version of the system submitted as a final project for the degree of MSc in Software Development in Queen's University of Belfast in September 2017.  The focus of refactoruing has been to apply OOP design principles and patterns and more effective and concise coding practices. 

Further background documentation is held in the MesosticsMachine-Archive repository on this GitHub site.  

As of 13 February 2018:

Classes in the NextItem and the NextItemFiltered hierarchies are complete.

The functioning of the syllable filter object, which now uses a ChromeDriver downloaded on 13 February 2018 with  Selenium 3.9.1 for Java, remains unstable, although the introduction of a timeout statement has helped.
  
The Sound class dealing with sounds has been refactored and needs some more testing. This class uses a OEDSounds file with words scraped from the Online Oxford English Dictionary and produces many flase positives.  

Classes dealing with places and mesostic formatting require further refactoring and testing.
 
The GUI is appropriately structured but each actionPerformed() method needs to be tested. 

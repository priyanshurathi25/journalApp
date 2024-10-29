package net.engineeringdigest.journalApp.controller;

import lombok.NonNull;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.services.UserService;
import net.engineeringdigest.journalApp.services.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
 public class journalEntrycontrollerV2 {
@Autowired
journalEntryService JournalEntryService;
@Autowired
 private UserService userService;
 @GetMapping
 public ResponseEntity<?> getAllJournalEntriesOfuser(){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  String userName = authentication.getName();
  User user = userService.FindByUserName(userName);
List<journalEntry> all = user.getJournalEnteries();
if (all!=null && !all.isEmpty()){
 return   new ResponseEntity<>(all , HttpStatus.OK);
}
return new ResponseEntity<>(all , HttpStatus.NOT_FOUND);
 }
 @PostMapping()
 public ResponseEntity<journalEntry> CreaeEntry(@RequestBody journalEntry MyEntry){
  try {
   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   String userName = authentication.getName();
  JournalEntryService.SaveEntry(MyEntry , userName);
return new ResponseEntity<>(MyEntry , HttpStatus.CREATED);
  }catch (Exception e){
   return new ResponseEntity<>(MyEntry , HttpStatus.BAD_REQUEST);
  }

 }
 @GetMapping("id/{myid}")
 public ResponseEntity<journalEntry> journalEntrybyid(@PathVariable ObjectId myid){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  String userName = authentication.getName();
  User user = userService.FindByUserName(userName);
  List<journalEntry> collect = user.getJournalEnteries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
  if (!collect.isEmpty()){
  Optional<journalEntry> JournalEntry =JournalEntryService.findbyId(myid);
  if (JournalEntry.isPresent()){
   return new ResponseEntity<>(JournalEntry.get() , HttpStatus.OK);
  }
  }
  return new ResponseEntity<>( HttpStatus.NOT_FOUND);
 }
 @DeleteMapping("id/{myid}")
 public ResponseEntity<?> journaldeletebyid(@PathVariable ObjectId myid ){

  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  String userName = authentication.getName();
JournalEntryService.DeletebyId(myid , userName);
return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }
 @PutMapping("/id/{id}")
 public ResponseEntity<?> updatejournalEntry(@PathVariable ObjectId myid,  @RequestBody journalEntry newEntry) {
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  String userName = authentication.getName();
  User user = userService.FindByUserName(userName);
  List<journalEntry> collect = user.getJournalEnteries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
  if (!collect.isEmpty()) {
   Optional<journalEntry> journalEntry = JournalEntryService.findbyId(myid);
   if (journalEntry.isPresent()) {
    journalEntry old = journalEntry.get();
    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
    old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());

   }
  }
  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 }
}

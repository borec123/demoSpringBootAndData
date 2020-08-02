package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.pojo.Watch;

@Component
class WatchModelAssembler implements RepresentationModelAssembler<Watch, EntityModel<Watch>> {

  @Override
  public EntityModel<Watch> toModel(Watch watch) {

    return new EntityModel<Watch>(watch, //
        linkTo(methodOn(Controller.class).one(watch.getId())).withSelfRel(),
        linkTo(methodOn(Controller.class).list()).withRel("list"));
  }
}

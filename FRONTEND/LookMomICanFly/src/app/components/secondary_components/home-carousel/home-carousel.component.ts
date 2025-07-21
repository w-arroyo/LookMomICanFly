import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';

@Component({
  selector: 'app-home-carousel',
  imports: [CommonModule, RouterLinkWithHref],
  templateUrl: './home-carousel.component.html',
  styleUrl: './home-carousel.component.css'
})
export class HomeCarouselComponent implements OnInit, OnDestroy {

  images: string[] = [
    'carousel0.gif',
    'carousel1.gif',
    'carousel2.gif',
    'carousel3.gif'
  ];

  currentIndex = 0;
  private interval: any;

  ngOnInit() {
    setTimeout(() => this.currentIndex = 0, 50);
    
    this.interval = setInterval(() => {
      this.nextSlide();
    }, 4500);
  }

  nextSlide() {
    this.currentIndex = (this.currentIndex + 1) % this.images.length;
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

}

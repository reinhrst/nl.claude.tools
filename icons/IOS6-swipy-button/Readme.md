I wanted some buttons like the delete button you get on IOS (IOS6) when you swipe some entry (try to swipe a phone number of one of your contacts in address book), and actually I wanted that button in some different colours. First try was CSS3. The button consists of 3 elements: A background gradient (to linear, but with some stops, easy to create in CSS3), a drop-shadow towards the top (easy to create in CSS3), and a border. The border however has a gradient in it as well, and I was unable to create that in CSS3 (in combination with rounded corners). So, SVG. Basically the SVG files are sprites, having the up and down state side to side, it will be easy to pull out one or the other. These are retina files; For instance the border is only 0.75 non-retina-pixels wide, and I'm sure that on no retina screens, the iPhone actually uses a different image.

The image comes without text, I think it's easiest to add the text through HTML and style through CSS. I made a SCSS mixin for the button. Note that it might not work on older browsers. Also note that this version directly uses the SVG as background image, which is not supported on all platforms

HTML:

    <div class="delete-button"><div>Delete</div></div>

SCSS:
        
    @mixin swipe-button($color) {
        $button-width: 64px; $button-height: 33px; $margin-right: 7px; $padding-x: 4px; $padding-top: 9px;
        width: $button-width + $margin-right; height: $button-height;
        float: right; overflow: hidden; position: relative; -webkit-transition: width 200ms;
        > div {
            width: $button-width - 2 * $padding-x; height: $button-height - $padding-top; overflow: hidden;
            position: absolute; right: 0px; margin-right: $margin-right;
            background-image: url(../img/buttons@2x.svg); background-size: 2*$button-width 3*$button-height;
            padding: $padding-top $padding-x 0; text-align: center; text-overflow: ellipsis; white-space: nowrap;
            font: normal 14px Arial, Helvetica; color: #FFF; text-shadow: 0 -1px 0 rgba(0,0,0,.5);
            background-position-y: if($color == green, -1, if($color == blue, -2, 0))* $button-height;
            /* somehow bold only looks good on retina screen */
            @media (-webkit-min-device-pixel-ratio: 1.5) {font-weight: bold;} 
        }

        &:not(:active) > div {-webkit-transition: background-position-x 0 ease 1000ms;}
        &:active > div {background-position-x: -$button-width;}
    }

    .delete-button {
        @include swipe-button(red); margin-top: 7px;
    }
    &:not(.show-delete) .delete-button {width: 0}


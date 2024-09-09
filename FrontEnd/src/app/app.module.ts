import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { HomeComponent } from "./components/home/home.component";
import { HeaderComponent } from "./components/header/header.component";
import { FooterComponent } from "./components/footer/footer.component";
import { OrderComponent } from "./components/order/order.component";
import { OrderDetailComponent } from "./components/order-confirm/order.detail.component";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";
import { DetailProductComponent } from "./components/detail-product/detail-product.component";
import { FormsModule } from "@angular/forms";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { TokenInterceptor } from "./interceptors/token.interceptors";
import { ReactiveFormsModule } from "@angular/forms";
import { AppComponent } from "./app/app.component";
import { AppRoutingModules } from "./app-routing.module";
import { NgbPopoverModule } from "@ng-bootstrap/ng-bootstrap";
import { UserProfileComponent } from "./components/user-profile/user.profile.component";

@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    OrderComponent,
    OrderDetailComponent,
    LoginComponent,
    RegisterComponent,
    DetailProductComponent,
    UserProfileComponent,
    AppComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModules,
    NgbPopoverModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
  bootstrap: [
    // HomeComponent,
    // OrderComponent,
    // OrderDetailComponent
    // LoginComponent,
    // RegisterComponent,
    // DetailProductComponent,
    // UserProfileComponent,
    AppComponent,
  ],
})
export class AppModule {}

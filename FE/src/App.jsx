import React from "react";
import {
    Route,
    RouterProvider,
    createBrowserRouter,
    createRoutesFromElements,
    useRouteLoaderData
} from "react-router-dom";
import Search from "./pages/Search/Search";
import Onboarding, {
    loader as onboardingLoader
} from "./pages/Onboarding/Onboarding";
import Login, {
    loginLoader,
    logout as logoutLoader
} from "./pages/Login/Login";

import DriverList from "./pages/DriverList/DriverList";
import { checkAuthLoader } from "./utils/auth";
import PickUpPage from "./pages/PickUp/PickUp";
import DriverDetail, {
    loader as driverDetailLoader
} from "./pages/DriverDetail/DriverDetail";
import ParentSignUp from "./pages/SignUp/ParentSignUp";
import { DriverMenu, ParentMenu, fetchMenuData } from "./pages/Menu/Menu";
import SubscriptionConfirmation from "./pages/Confirmation/Confirmation";
import ParentMap from "./components/Map/ParentMap";
import DriverMap, { fetchNowPickUpData } from "./components/Map/DriverMap";
import EndPickUp from "./pages/EndPickUp/EndPickUp";
import ManagementSubscription from "./pages/DriverSubsPage/Management";

export default function App() {
    return <RouterProvider router={router} />;
}

const router = createBrowserRouter(
    createRoutesFromElements(
        <>
            <Route
                path="/"
                loader={onboardingLoader}
                element={<Onboarding />}
            />
            <Route path="signup" element={<ParentSignUp />} />
            <Route path="login" loader={loginLoader} element={<Login />} />
            <Route id="auth" loader={checkAuthLoader} errorElement={<Login />}>
                <Route path="logout" loader={logoutLoader} />
                <Route
                    path="map"
                    element={
                        <RoleProvider>
                            {(isParent) =>
                                isParent ? <ParentMap /> : <DriverMap />
                            }
                        </RoleProvider>
                    }
                />
                <Route
                    path="menu"
                    loader={fetchMenuData}
                    element={
                        <RoleProvider>
                            {(isParent) =>
                                isParent ? <ParentMenu /> : <DriverMenu />
                            }
                        </RoleProvider>
                    }
                />
                <Route
                    path="subscription/search"
                    element={
                        <RoleProvider>
                            {(isParent) => isParent && <Search />}
                        </RoleProvider>
                    }
                />
                <Route
                    path="subscription/drivers"
                    element={
                        <RoleProvider>
                            {(isParent) => isParent && <DriverList />}
                        </RoleProvider>
                    }
                />
                <Route
                    path="subscription/driver/:driverId"
                    loader={driverDetailLoader}
                    element={
                        <RoleProvider>
                            {(isParent) => isParent && <DriverDetail />}
                        </RoleProvider>
                    }
                />
                <Route
                    path="subscription/confirmation"
                    element={
                        <RoleProvider>
                            {(isParent) =>
                                isParent && <SubscriptionConfirmation />
                            }
                        </RoleProvider>
                    }
                />
                <Route
                    path="pickup"
                    element={
                        <RoleProvider>
                            {(isParent) => !isParent && <PickUpPage />}
                        </RoleProvider>
                    }
                />
                <Route
                    path="endpickup"
                    element={
                        <RoleProvider>
                            {(isParent) => !isParent && <EndPickUp />}
                        </RoleProvider>
                    }
                />

                <Route
                    path="pickup/request"
                    element={
                        <RoleProvider>
                            {(isParent) =>
                                !isParent && <ManagementSubscription />
                            }
                        </RoleProvider>
                    }
                />
            </Route>
        </>
    )
);

function RoleProvider({ children }) {
    const role = useRouteLoaderData("auth");
    return (
        <>
            {typeof children === "function"
                ? children(role === "PARENT")
                : children}
        </>
    );
}

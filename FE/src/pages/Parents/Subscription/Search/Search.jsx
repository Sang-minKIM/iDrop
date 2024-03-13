import { useReducer, useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Search.module.scss";
import { Header } from "@/components/Header/Header";
import { Footer } from "@/components/Footer/Footer";
import { AddressForm } from "./AddressForm/AddressForm";
import { DayList } from "./DayList/DayList";
import { TimeList } from "./TimeList/TimeList";
import { useModal } from "@/hooks/useModal";
import { MapModal } from "./MapModal/MapModal";

export default function Search() {
    const [schedule, setSchedule] = useState({});
    const [mapType, setMapType] = useState("departure");
    const [detailAddress, setDetailAddress] = useState({
        departure: "",
        destination: ""
    });

    const [location, dispatch] = useReducer(locationReducer, {
        departure: { ...INITIAL_LOCATION_STATE },
        destination: { ...INITIAL_LOCATION_STATE }
    });

    const navigate = useNavigate();

    const { isVisible, open: openModal, close: closeModal } = useModal();

    const handleOpenModal = ({ target: { name } }) => {
        setMapType(name);
        openModal();
    };

    const handleLocationSelect = (data, mapType) => {
        dispatch({ type: mapType, payload: data });
    };

    const handleScheduleChange = (day, unit) => (value) => {
        setSchedule((prevSchedule) => ({
            ...prevSchedule,
            [day]: { ...prevSchedule[day], [unit]: Number(value) }
        }));
    };

    function transformLocationData({ departure, destination }) {
        return {
            startAddress: departure.address,
            startLatitude: departure.latitude,
            startLongitude: departure.longitude,
            endAddress: destination.address,
            endLatitude: destination.latitude,
            endLongitude: destination.longitude
        };
    }

    const isButtonActive =
        location.departure.address &&
        location.destination.address &&
        Object.keys(schedule).length > 0;

    const handleSubmit = (isButtonActive, location, schedule) => {
        if (!isButtonActive) {
            return;
        }
        const locationData = transformLocationData(location);
        navigate("/subscription/drivers", {
            state: { ...locationData, schedule }
        });
    };

    const handleDetailAddressChange = ({ target: { value } }) => {
        setDetailAddress((prev) => ({
            ...prev,
            [mapType]: value
        }));
    };

    return (
        <>
            <main className={styles.container}>
                <Header title="픽업 신청" to="/" />
                <section className={styles.contents}>
                    <AddressForm
                        handleOpenModal={handleOpenModal}
                        location={location}
                        detailAddress={detailAddress}
                    />
                    <DayList schedule={schedule} setSchedule={setSchedule} />
                    <TimeList
                        schedule={schedule}
                        handleScheduleChange={handleScheduleChange}
                    />
                </section>
                <Footer
                    text="확인"
                    onClick={() =>
                        handleSubmit(isButtonActive, location, schedule)
                    }
                    isButtonDisabled={!isButtonActive}
                />
                <MapModal
                    isVisible={isVisible}
                    onClose={closeModal}
                    handleDetailAddressChange={handleDetailAddressChange}
                    handleLocationSelect={handleLocationSelect}
                    mapType={mapType}
                    location={location}
                    detailAddress={detailAddress}
                />
            </main>
        </>
    );
}

const INITIAL_LOCATION_STATE = {
    address: "",
    latitude: "",
    longitude: ""
};

const locationReducer = (state, action) => {
    switch (action.type) {
        case "departure":
            return {
                ...state,
                departure: action.payload
            };
        case "destination":
            return {
                ...state,
                destination: action.payload
            };
        default:
            return state;
    }
};

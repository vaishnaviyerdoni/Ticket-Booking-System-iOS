package com.sunbeam.tikito.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.sunbeam.tikito.daos.BookedSeatsDao;
import com.sunbeam.tikito.daos.BookingDao;
import com.sunbeam.tikito.daos.SeatDao;
import com.sunbeam.tikito.daos.ShowDao;
import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.daos.VenueDao;
import com.sunbeam.tikito.dto.AllBookingsDto;
import com.sunbeam.tikito.dto.AvailableSeatsDto;
import com.sunbeam.tikito.dto.BookingHistoryDto;
import com.sunbeam.tikito.dto.CancelTicketDto;
import com.sunbeam.tikito.dto.CheckSeatDto;
import com.sunbeam.tikito.dto.TicketBookedDto;
import com.sunbeam.tikito.dto.TicketBookingDto;
import com.sunbeam.tikito.dto.UserBookingDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.entity.VenueEntity;
import com.sunbeam.tikito.enums.BookingStatus;
import com.sunbeam.tikito.enums.PaymentStatus;
import com.sunbeam.tikito.exceptions.InvalidBookingException;
import com.sunbeam.tikito.exceptions.InvalidSeatsException;
import com.sunbeam.tikito.exceptions.InvalidShowException;
import com.sunbeam.tikito.exceptions.UserNotFoundException;
import com.sunbeam.tikito.services.BookingService;
import com.sunbeam.tikito.entity.BookedSeatsEntity;
import com.sunbeam.tikito.entity.BookingEntity;
import com.sunbeam.tikito.entity.SeatEntity;
import com.sunbeam.tikito.entity.ShowEntity;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class BookingServiceImpl implements BookingService 
{	
	private UserDao userDao;
	private ShowDao showDao;
	private SeatDao seatDao;
	private BookedSeatsDao bookedSeatDao;
	private BookingDao bookingDao;
	private ModelMapper mapper;
	private VenueDao venueDao;
	
	public BookingServiceImpl(UserDao userDao, ShowDao showDao, SeatDao seatDao, BookedSeatsDao bookedSeatDao, BookingDao bookingDao, ModelMapper mapper, VenueDao venueDao)
	{
		this.userDao = userDao;
		this.showDao = showDao;
		this.seatDao = seatDao;
		this.bookedSeatDao = bookedSeatDao;
		this.bookingDao = bookingDao;
		this.mapper = mapper;
		this.venueDao = venueDao;
	}

	@Override
	public TicketBookedDto bookTicket(TicketBookingDto dto, long userId) 
	{
		TicketBookedDto ticket = new TicketBookedDto();
		
		//get the user
		UserEntity loggedInUser = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User cannot be found"));
		
		//get the show for which user wants to book tickets for
		ShowEntity show = showDao.findById(dto.getShowId()).orElseThrow(() -> new InvalidShowException("Show is not Available"));
		
		//get the venue from show to verify that belongs to venue
		long venueId = show.getVenue().getVenueId();
		
		//check if showDate is valid before proceeding to book
		if(isDateValid(show))
		{
			//get the list of seasts from db and dto and make sure their size matches
			//if the size matched then the seats are valid because all the requested seats were received
			//if size is different that means some of requested seats were not returned because they didnt exist or are booked
			List<SeatEntity> seats = seatDao.findBySeatIdIn(dto.getSeatIds());
			if(seats.size() != dto.getSeatIds().size())
			{
				throw new InvalidShowException("Seats are invalid");
			}
			else
			{
				//now that we checked seats are valid, proceed to book
				//firstly validate venue
				for(SeatEntity s : seats)
				{
					if(s.getVenue().getVenueId() != venueId)
					{
						throw new InvalidShowException("The seats are invalid");
					}
				}
				
				//seats and show belong to the given venue, now check if seats are actually booked or not
				//fetch list of bookedSeats for given array of seat Ids, if list is empty, seats can booked else already booked
				List<BookedSeatsEntity> bookedSeats = bookedSeatDao.findByShowShowIdAndSeatSeatIdIn(dto.getShowId(), dto.getSeatIds());
				if(!bookedSeats.isEmpty())
				{
					throw new InvalidShowException("Seats are already booked");
				}
				
				//if seats are not booked, proceed booking
				//1. calculate price of tickets
				Double totalAmt = show.getPrice() * seats.size();
				List<BookedSeatsEntity> newBookingSeats = new ArrayList<>();
				
				//2. Create a booking Object 
				BookingEntity booking = new BookingEntity(null, loggedInUser, show, new ArrayList<>(), totalAmt, PaymentStatus.PAID, BookingStatus.SUCCESS);
				
				//3. add newly booked seats to bookedSeats table
				for(SeatEntity s : seats)
				{
					BookedSeatsEntity bs = new BookedSeatsEntity(null, booking, s, show);
					newBookingSeats.add(bs);
				}
				
				//4. make final booking
				try
				{
					//add bookedSeats to booking object
					booking.setBookedSeats(newBookingSeats);
					
					//finally save the booking object in the database
					BookingEntity newBooking = bookingDao.save(booking);
					
					//create string array to fetch the seat nums
					List<String> seatNums = new ArrayList<>();
					
					//finally save all bookedseats in databse
					List<BookedSeatsEntity> newlyBookedSeats = bookedSeatDao.saveAll(newBookingSeats);
					
					//Loop through fetch seat nums and save it in seats nuims list
					for(BookedSeatsEntity bs : newlyBookedSeats)
					{
						seatNums.add(bs.getSeat().getSeatNo());
					}
					
					//initialize ticket and return 
					ticket = new TicketBookedDto(
					        newBooking.getBookingId(),
					        show.getShowId(),
					        show.getEvent().getEventName(),
					        show.getVenue().getName(),
					        show.getShowDate(),
					        show.getShowStartTime(),
					        seatNums,
					        totalAmt,
					        PaymentStatus.PAID,
					        BookingStatus.SUCCESS);
				}
				catch(DataIntegrityViolationException e)
				{
					throw new InvalidSeatsException("Seats are already booked");
				}
			}
		}
		else
		{
			throw new InvalidShowException("The show has already started, can't book now");
		}
		
		return ticket;
	}

	@Override
	public CancelTicketDto cancelTicket(long bookingId, long userId) 
	{
		CancelTicketDto cancelledTicket = new CancelTicketDto();
		BookingEntity booking = bookingDao.findByBookingIdAndUserUserId(bookingId, userId).orElseThrow(() -> new InvalidBookingException("Booking unavailable"));
		
		if(booking.getBookingStatus().equals(BookingStatus.CANCELLED))
		{
			throw new InvalidBookingException("Ticket booking is already cancelled");
		}
		else
		{
			ShowEntity show = booking.getShow();
			if(isDateValid(show))
			{
				booking.setBookingStatus(BookingStatus.CANCELLED);
				booking.setPaymentStatus(PaymentStatus.REFUNDED);
				
				bookingDao.save(booking);
				bookedSeatDao.deleteByBookingBookingId(bookingId);
				
				cancelledTicket = new CancelTicketDto(bookingId, booking.getBookingStatus(), booking.getPaymentStatus());
			}
			else
			{
				throw new InvalidShowException("The show has already started");
			}
		}
		
		return cancelledTicket;
	}
	
	public boolean isDateValid(ShowEntity show)
	{
		LocalDate sDate = show.getShowDate();
		LocalTime sTime = show.getShowStartTime(); 
		
		if(LocalDate.now().isBefore(sDate))
			return true;
		else if(LocalDate.now().isEqual(sDate))
		{
			if(LocalTime.now().isBefore(sTime))
			{
				return true;
			}
			else 
				return false;
		}
		else
			return false;
	}

	@Override
	public UserBookingDto getBookingsByUser(long bookingId, long userId) 
	{
		UserBookingDto bookingDetails = new UserBookingDto();
		BookingEntity b = bookingDao.findByBookingIdAndUserUserId(bookingId, userId)
			              				  .orElseThrow(() -> new InvalidBookingException("Booking unavailable"));
		
		List<String> seatNums = new ArrayList<>();
		List<BookedSeatsEntity> seats = b.getBookedSeats();
		for(BookedSeatsEntity bs : seats)
		{
			seatNums.add(bs.getSeat().getSeatNo());
		}
		
		bookingDetails = new UserBookingDto(b.getBookingId(), 
				b.getShow().getShowId(),
				b.getShow().getEvent().getEventName(),
				b.getShow().getVenue().getName(),
				b.getShow().getShowDate(),
				b.getShow().getShowStartTime(),
				b.getShow().getShowEndTime(),
				b.getTotalAmt(), 
				seatNums, 
				b.getPaymentStatus(), 
				b.getBookingStatus(), 
				b.getCreatedAt());
		return bookingDetails;
	}

	@Override
	public List<UserBookingDto> getAllBookingsByUser(long userId)
	{
		UserBookingDto bookingDetails = new UserBookingDto();
		List<UserBookingDto> bookingList = new ArrayList<>();
		List<BookingEntity> bookings = bookingDao.findByUserUserId(userId);
		
		for(BookingEntity b : bookings)
		{
			List<String> seatNums = new ArrayList<>();
			List<BookedSeatsEntity> seats = b.getBookedSeats();
			for(BookedSeatsEntity bs : seats)
			{
				seatNums.add(bs.getSeat().getSeatNo());
			}
			
			bookingDetails = new UserBookingDto(b.getBookingId(), 
												b.getShow().getShowId(),
												b.getShow().getEvent().getEventName(),
												b.getShow().getVenue().getName(),
												b.getShow().getShowDate(),
												b.getShow().getShowStartTime(),
												b.getShow().getShowEndTime(),
												b.getTotalAmt(), 
												seatNums, 
												b.getPaymentStatus(), 
												b.getBookingStatus(), 
												b.getCreatedAt());
			
			bookingList.add(bookingDetails);
		}
		
		return bookingList;
	}
	
	

	@Override
	public List<AllBookingsDto> getAllBookingsByShow(long showId) 
	{
		List<AllBookingsDto> dtos = new ArrayList<>();
		List<BookingEntity> bookings = bookingDao.findByShowShowId(showId);
		for(BookingEntity b : bookings)
		{
			AllBookingsDto dto = mapper.map(b, AllBookingsDto.class);
			dto.setShowId(showId);
			dto.setUserId(b.getUser().getUserId());
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public List<AvailableSeatsDto> getAllAvailableSeats(long showId) 
	{
		ShowEntity show = showDao.findById(showId).orElseThrow(() -> new InvalidShowException("Show is unavailable"));
		VenueEntity venue = show.getVenue();
		
		List<SeatEntity> allSeats = venue.getSeatList();
		List<BookedSeatsEntity> bookedSeats = bookedSeatDao.findByShowShowId(showId);
		
		Set<Long> bookedSeatIds = bookedSeats.stream()
		        				  .map(bs -> bs.getSeat().getSeatId())
		                          .collect(Collectors.toSet());
		
		List<SeatEntity> availableSeats = allSeats.stream()
		                                  .filter(s -> !bookedSeatIds.contains(s.getSeatId())).toList();
		
		List<AvailableSeatsDto> dtos = new ArrayList<>();
		for(SeatEntity seat: availableSeats)
		{
			AvailableSeatsDto dto =
	                new AvailableSeatsDto();

	        dto.setSeatId(seat.getSeatId());

	        dto.setSeatNo(seat.getSeatNo());

	        dto.setBooked(
	                bookedSeatIds.contains(
	                        seat.getSeatId()));

	        dtos.add(dto);
		}
		
		return dtos;
	}
	
	@Override
	public List<BookingHistoryDto> getBookingHistory(long userId) {

	    List<BookingEntity> bookings =
	            bookingDao.findByUserUserId(userId);

	    List<BookingHistoryDto> bookingHistory = new ArrayList<>();

	    for (BookingEntity booking : bookings) {

	        List<String> seatNumbers = new ArrayList<>();

	        if (booking.getBookedSeats() != null) {
	            for (BookedSeatsEntity bookedSeat : booking.getBookedSeats()) {
	                seatNumbers.add(bookedSeat.getSeat().getSeatNo());
	            }
	        }

	        BookingHistoryDto dto = new BookingHistoryDto();

	        dto.setBookingId(booking.getBookingId());

	        dto.setShowId(booking.getShow().getShowId());

	        dto.setEventName(
	                booking.getShow().getEvent().getEventName());

	        dto.setVenueName(
	                booking.getShow().getVenue().getName());

	        dto.setShowDate(
	                booking.getShow().getShowDate());

	        dto.setShowStartTime(
	                booking.getShow().getShowStartTime());

	        dto.setSeatNumbers(seatNumbers);

	        dto.setTotalAmt(booking.getTotalAmt());

	        dto.setPaymentStatus(
	                booking.getPaymentStatus());

	        dto.setBookingStatus(
	                booking.getBookingStatus());

	        dto.setBookingDate(
	                booking.getCreatedAt());

	        bookingHistory.add(dto);
	    }

	    return bookingHistory;
	}
	
	@Override
	public boolean areSeatsAvailable(CheckSeatDto dto)
	{
	    List<BookedSeatsEntity> bookedSeats =
	            bookedSeatDao.findByShowShowIdAndSeatSeatIdIn(
	                    dto.getShowId(),
	                    dto.getSeatIds());

	    return bookedSeats.isEmpty();
	}
}
